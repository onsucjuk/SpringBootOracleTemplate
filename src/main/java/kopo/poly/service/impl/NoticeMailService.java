package kopo.poly.service.impl;

import kopo.poly.dto.MailDTO;
import kopo.poly.persistance.mapper.INoticeMailMapper;
import kopo.poly.service.INoticeMailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class NoticeMailService implements INoticeMailService {

    private final INoticeMailMapper noticeMailMapper;

    @Transactional
    @Override
    public List<MailDTO> getNoticeMailList() throws Exception {
        log.info(this.getClass().getName() + ".getNoticeMailList start!");

        return noticeMailMapper.getNoticeMailList();
    }

    @Override
    public void insertNoticeMailInfo(MailDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".InsertNoticeInfo start!");

        noticeMailMapper.insertNoticeMailInfo(pDTO);
    }

}
