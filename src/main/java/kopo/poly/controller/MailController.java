package kopo.poly.controller;

import kopo.poly.dto.MailDTO;
import kopo.poly.dto.MsgDTO;
import kopo.poly.dto.NoticeDTO;
import kopo.poly.service.IMailService;
import kopo.poly.service.INoticeMailService;
import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value="/mail")
/* /mail로 들어오는 모든 명령 처리하겠다. */
@Controller

public class MailController {

    private final IMailService mailService; // 메일 발송을 위한 서비스 객체를 사용하기
    private final INoticeMailService noticeMailService;

    /**
     * 메일 발송하기 폼
     */

    @GetMapping(value = "mailForm")
    public String mailForm() throws Exception {

        // 로그 찍기
        log.info(this.getClass().getName() + "mailForm Start!");

        return "/mail/mailForm";
    }

    /**
     * 메일 발송하기
     */

    @ResponseBody
    /* Ajax -> Json 형식 변경 */
    @PostMapping(value = "sendMail")
    public MsgDTO sendMail(HttpServletRequest request, ModelMap model) throws Exception {
        log.info(this.getClass().getName() + ".sendMail Start!");

        String msg = ""; // 발송 결과 메세지

        // 웹 URL로부터 전달 받는 값들
        String toMail = CmmUtil.nvl(request.getParameter("toMail")); // 받는 사람
        String title = CmmUtil.nvl(request.getParameter("title")); // 제목
        String contents = CmmUtil.nvl(request.getParameter("contents")); // 내용

        /*
         * 들어온 값 확인
         */

        log.info("toMail : " + toMail);
        log.info("title : " + title);
        log.info("contents " + contents);

        // 메일 발송할 정보 넣기 위한 DTO객체 생성하기
        MailDTO pDTO = new MailDTO();

        // 웹에서 받은 값을 DTO에 넣기
        pDTO.setToMail(toMail); // 받는 사람을 DTO에 저장
        pDTO.setTitle(title); // 제목을 DTO에 저장
        pDTO.setContents(contents); // 내용을 DTO에 저장

        //메일 발송하기
        int res = mailService.doSendMail(pDTO);

        if(res == 1) { // 메일 발송 성공
            msg = "메일 발송하였습니다.";
        } else { //메일발송 실패
            msg = "메일 발송 실패하였습니다.";
        }

        log.info(msg);

        //결과 메세지 전달하기
        MsgDTO dto = new MsgDTO();
        dto.setMsg(msg);

        //로그 찍기(추후 찍은 로그를 통해 이 함수 호출이 끝났는지 파악하기 용이하다.
        log.info(this.getClass().getName() + ".sendMail End!");

        return dto;
    }

    /**
     * 메일 리스트 보여주기
     * <p>
     * GetMapping(value = "mail/mailList") =>  GET방식을 통해 접속되는 URL이 mail/mailList 경우 아래 함수를 실행함
     */
    @GetMapping(value = "mailList")
    public String noticeList(HttpSession session, ModelMap model)
            throws Exception {

        // 로그 찍기(추후 찍은 로그를 통해 이 함수에 접근했는지 파악하기 용이하다.)
        log.info(this.getClass().getName() + ".mailList Start!");



        // 공지사항 리스트 조회하기
        // Java 8부터 제공되는 Optional 활용하여 NPE(Null Pointer Exception) 처리
        List<MailDTO> rList = Optional.ofNullable(noticeMailService.getNoticeMailList())
                .orElseGet(ArrayList::new);

//        List<NoticeDTO> rList = noticeService.getNoticeList();
//
//        if (rList == null) {
//            rList = new ArrayList<>();
//        }

        // 조회된 리스트 결과값 넣어주기
        model.addAttribute("rList", rList);

        // 로그 찍기(추후 찍은 로그를 통해 이 함수 호출이 끝났는지 파악하기 용이하다.)
        log.info(this.getClass().getName() + ".mailList End!");

        // 함수 처리가 끝나고 보여줄 JSP 파일명
        // webapp/WEB-INF/views/notice/noticeList.jsp
        return "mail/mailList";

    }
}
