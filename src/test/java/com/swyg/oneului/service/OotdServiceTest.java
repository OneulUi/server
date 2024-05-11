package com.swyg.oneului.service;

import com.swyg.oneului.model.Member;
import com.swyg.oneului.model.MemberRole;
import com.swyg.oneului.model.Ootd;
import com.swyg.oneului.repository.OotdRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OotdServiceTest {
    @Mock
    private OotdRepository ootdRepository;

    @Mock
    private OotdImageService ootdImageService;

    @Mock
    private BookMarkOotdService bookMarkOotdService;

    @Mock
    private LikeOotdService likeOotdService;

    @InjectMocks
    private OotdService ootdService;

    @Test
    public void PK로_OOTD를_조회한다() throws Exception {
        // given
        long ootdId = 1001L;
        Ootd ootdFixture = createOotdFixture(ootdId);
        when(ootdRepository.findById(ootdId)).thenReturn(Optional.of(ootdFixture));

        // when
        Ootd foundOotd = ootdService.findOotdById(ootdId);

        // then
        assertThat(ootdId).isEqualTo(foundOotd.getOotdId());
    }

    @Test
    public void 조회된_OOTD가_없으면_예외() throws Exception {
        // given
        long ootdId = 1001L;
        when(ootdRepository.findById(ootdId)).thenReturn(Optional.empty());

        // when
        // then
        Assertions.assertThatThrownBy(() -> ootdService.findOotdById(ootdId))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void 사용자와_PK로_OOTD_조회() throws Exception {
        // given
        long ootdId = 1001L;
        String loginId = "myLoginId";
        Member member = creatMemberFixtureWithoutSurvey(loginId);
        Ootd ootdFixture = createOotdFixture(ootdId); // 테스트에 필요한 적절한 OOTD 생성
        when(ootdRepository.findOotdByMemberAndOotdId(member, ootdId)).thenReturn(ootdFixture);

        // when
        Ootd foundOotd = ootdService.findOotdByMemberAndOotdId(member, ootdId);

        // then
        assertThat(foundOotd).isNotNull(); // OOTD가 null이 아닌지 확인
        assertThat(foundOotd.getOotdId()).isEqualTo(ootdId); // 반환된 OOTD의 ID가 예상한 것과 일치하는지 확인
    }

    @Test
    public void Ootd_업데이트() throws Exception {
        // given
        long ootdId = 1001L;
        Ootd ootdFixture = createOotdFixture(ootdId);
        Ootd newOotd = new Ootd(ootdId, "new review", "new temperature", "new humidity", "new satisfaction");
        MultipartFile image = createMultipartFileFixture();

        // when
        ootdService.updateOotd(ootdFixture, newOotd, image);

        // then
        assertThat(ootdFixture.getReview()).isEqualTo("new review");
        assertThat(ootdFixture.getTemperature()).isEqualTo("new temperature");
        assertThat(ootdFixture.getHumidity()).isEqualTo("new humidity");
        assertThat(ootdFixture.getSatisfaction()).isEqualTo("new satisfaction");

        verify(ootdImageService, Mockito.times(1)).replaceOotdImage(ootdFixture, image);
    }

    @Test
    public void OOTD_삭제() {
        // given
        Ootd ootd = createOotdFixture(1001L);

        // when
        ootdService.deleteOotdById(ootd);

        // then
        verify(likeOotdService, Mockito.times(1)).deleteLikeOotdByOotd(ootd); // likeOotdService.deleteLikeOotdByOotd()가 한 번 호출되었는지 확인
        verify(bookMarkOotdService, Mockito.times(1)).deleteBookMarkOotdByOotd(ootd); // bookMarkOotdService.deleteBookMarkOotdByOotd()가 한 번 호출되었는지 확인
        verify(ootdImageService, Mockito.times(1)).deleteOotdImage(ootd); // ootdImageService.deleteOotdImage()가 한 번 호출되었는지 확인
        verify(ootdRepository, Mockito.times(1)).delete(ootd); // ootdRepository.delete()가 한 번 호출되었는지 확인
    }


    @Test
    public void 모든_ootd_조회() throws Exception {
        // given
        List<Ootd> ootdListFixture = createOotdListFixture();
        when(ootdRepository.findAll()).thenReturn(ootdListFixture);

        // when
        List<Ootd> foundOotdList = ootdService.findAllOotds();

        // then
        assertThat(foundOotdList).isNotNull();
        assertThat(foundOotdList.size()).isEqualTo(ootdListFixture.size());
    }

    @Test
    public void 온도로_OOTD_조회() {
        // given
        String temperature = "20";
        List<Ootd> ootdListFixture = createOotdListFixture();
        when(ootdRepository.findAllOotdsByTemperature(temperature)).thenReturn(ootdListFixture);

        // when
        List<Ootd> foundOotdList = ootdService.findAllOotdsByTemperature(temperature);

        // then
        assertThat(foundOotdList).isNotNull();
        assertThat(foundOotdList.size()).isEqualTo(ootdListFixture.size());
    }

    @Test
    public void 습도로_OOTD_조회() {
        // given
        String humidity = "30";
        List<Ootd> ootdListFixture = createOotdListFixture();
        when(ootdRepository.findAllOotdsByHumidity(humidity)).thenReturn(ootdListFixture);

        // when
        List<Ootd> foundOotdList = ootdService.findAllOotdsByHumidity(humidity);

        // then
        assertThat(foundOotdList).isNotNull();
        assertThat(foundOotdList.size()).isEqualTo(ootdListFixture.size());
    }

    @Test
    public void 온도와_습도로_OOTD_조회() {
        // given
        String temperature = "20";
        String humidity = "30";
        List<Ootd> ootdListFixture = createOotdListFixture();
        when(ootdRepository.findAllOotdsByTemperatureAndHumidity(temperature, humidity)).thenReturn(ootdListFixture);

        // when
        List<Ootd> foundOotdList = ootdService.findAllOotdsByTemperatureAndHumidity(temperature, humidity);

        // then
        assertThat(foundOotdList).isNotNull();
        assertThat(foundOotdList.size()).isEqualTo(ootdListFixture.size());
    }

    private Ootd createOotdFixture(long ootdId) {
        return Ootd.builder()
                .ootdId(ootdId)
                .review("mock review")
                .temperature("20")
                .humidity("30")
                .satisfaction("1")
                .build();
    }

    private List<Ootd> createOotdListFixture() {
        List<Ootd> ootdList = new ArrayList<>();
        ootdList.add(createOotdFixture(1001L));
        ootdList.add(createOotdFixture(1002L));
        ootdList.add(createOotdFixture(1003L));
        return ootdList;
    }

    private Member creatMemberFixtureWithoutSurvey(String loginId) {
        return Member.builder()
                .memberId(1001L)
                .email("member@gmail.com")
                .name("오늘의")
                .loginId(loginId)
                .provider("GOOGLE")
                .role(MemberRole.USER)
                .build();
    }

    private MultipartFile createMultipartFileFixture() {
        return new MockMultipartFile("file", "filename.txt", "text/plain", "content".getBytes());
    }

}
