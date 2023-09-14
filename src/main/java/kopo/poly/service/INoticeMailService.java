package kopo.poly.service;

import kopo.poly.dto.MailDTO;

import java.util.List;

public interface INoticeMailService {

    /**
     * 메일 리스트
     *
     * @return 조회 경과
     */
    List<MailDTO> getNoticeMailList() throws Exception;

    /**
     *
     *  메일 정보 저장
     *
     */

    void insertNoticeMailInfo(MailDTO pDTO) throws Exception;


}
