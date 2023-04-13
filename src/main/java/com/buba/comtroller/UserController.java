package com.buba.comtroller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.buba.pojo.User;
import com.buba.service.UserService;
import com.buba.utils.R;
import com.buba.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private  JavaMailSenderImpl javaMailSender;

    @Autowired
    private UserService userService;

    @Value("${spring.mail.username}")
    private static String sendMailer;

    //发送邮箱验证码
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session){
        //获取邮箱
        String email =user.getEmail();
        if(!StringUtils.isEmpty(email)) {
            //生成随机的4位验证码
            String code = ValidateCodeUtils.generateCode().toString();
            log.info("code={}",code);
            //完成发送短信
//            sendMessage("瑞吉外卖","验证码", email,code);

            //需要将生成的验证码保存到Session
            session.setAttribute(email,code);
            return R.success("邮箱短信发送成功");
        }
        return R.error("邮箱短信发送失败");
    }



    //登录
    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession session) {
        log.info("map:{}", map.toString());
        //获取手机号
        String email = map.get("phone").toString();
        //获取验证码
        String code = map.get("code").toString();
        //从Session中获取保存的验证码
        Object codeInSession = session.getAttribute(email);
        //进行验证码比对（页面提交的验证码和Session中保存的验证码比对）
        if (codeInSession != null && codeInSession.equals(code)) {
            //如果能够比对成功，说明登录成功

            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getEmail, email);
            User user = userService.getOne(queryWrapper);
            if (user == null) {
                //判断当前手机号是否为新用户，如果是新用户则自动完成注册
                user = new User();
                user.setEmail(email);
                user.setStatus(1);
                userService.save(user);
            }
            session.setAttribute("user",user.getId());
            return R.success(user);
        }
        return R.error("登陆失败");
    }



    /**
     * 发送短信
     * @param signName 签名
     * @param templateCode 模板
     * @param emailNumbers 邮箱号
     * @param param 参数
     */
    public  void sendMessage(String signName, String templateCode,String emailNumbers,String param){
//        EmailUtils.sendMessage("瑞吉外卖","验证码",phone,code);
        try {
            //true 代表支持复杂的类型
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(javaMailSender.createMimeMessage(),true);
            //邮件发信人
//            mimeMessageHelper.setFrom(sendMailer);
            mimeMessageHelper.setFrom("2317104867@qq.com");
            //收件人
            mimeMessageHelper.setTo(emailNumbers);
            //邮件主题
            mimeMessageHelper.setSubject(signName);
            //邮件内容
            mimeMessageHelper.setText(templateCode+ ":"+param);
            //邮件发送时间
            mimeMessageHelper.setSentDate(new Date());
            //发送邮件
            javaMailSender.send(mimeMessageHelper.getMimeMessage());
            System.out.println("发送邮件成功：");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("发送邮件失败："+e.getMessage());
        }

    }

}
