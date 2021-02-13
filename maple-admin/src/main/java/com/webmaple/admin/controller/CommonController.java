package com.webmaple.admin.controller;

import com.webmaple.admin.container.WorkerContainer;
import com.webmaple.admin.model.Source;
import com.webmaple.admin.model.User;
import com.webmaple.admin.service.*;
import com.webmaple.common.enums.NodeType;
import com.webmaple.common.model.*;
import com.webmaple.common.util.RequestUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lyifee
 * on 2021/1/6
 */
@Controller
public class CommonController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonController.class);

    @Resource
    private ComponentService componentService;

    @Resource
    private UserService userService;

    @Resource
    private SourceService sourceService;

    @Resource
    private WorkerContainer workerContainer;

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/spider")
    public String spider() {
        return "spider";
    }

    @RequestMapping("/node")
    public String node() {
        return "node";
    }

    @RequestMapping("/timed")
    public String timed() {
        return "timed";
    }

    @RequestMapping("/user")
    public String user() {
        return "user";
    }

    @RequestMapping("/auth")
    public String auth() {
        return "auth";
    }

    @RequestMapping("/template")
    public String template() {
        return "template";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/doLogin")
    @ResponseBody
    public Result<Void> doLogin(@RequestParam String phone, @RequestParam String password, HttpServletResponse response) {
        Result<Void> result = new Result<>();
        if (StringUtils.isBlank(phone)) {
            return result.fail("手机号不能为空");
        }
        if (StringUtils.isBlank(password)) {
            return result.fail("密码不能为空");
        }
        User user = new User();
        user.setPhone(phone);
        user.setPassword(password);
        result = userService.login(user);
        if (result.getSuccess()) {
            response.addCookie(new Cookie("user", phone));
        }
        return result;
    }

    @PostMapping("/register")
    @ResponseBody
    public Result<Void> register(@RequestParam String phone, @RequestParam String nickname, @RequestParam String password) {
        Result<Void> result = new Result<>();
        if (StringUtils.isBlank(phone)) {
            return result.fail("手机号不能为空");
        }
        if (StringUtils.isBlank(nickname)) {
            return result.fail("用户昵称不能为空");
        }
        if (StringUtils.isBlank(password)) {
            return result.fail("密码不能为空");
        }
        User user = new User();
        user.setPhone(phone);
        user.setPassword(password);
        user.setNickname(nickname);
        return userService.register(user);
    }

    @GetMapping("/getNickname")
    @ResponseBody
    public Result<Void> getNickname(@RequestParam String phone) {
        Result<Void> result = new Result<>();
        if (StringUtils.isBlank(phone)) {
            return result.fail("登录状态异常，无法获取用户信息");
        }
        return userService.getNickname(phone);
    }

    @PostMapping("/addSource")
    @ResponseBody
    public Result<Void> addSource(@RequestParam String sourceName,
                                  @RequestParam String sourceType, @RequestParam String sourceIp,
                                  @RequestParam String account, @RequestParam String pass) {
        Result<Void> result = new Result<>();
        if (StringUtils.isBlank(sourceName) || StringUtils.isBlank(sourceType) || StringUtils.isBlank(sourceIp)
            || StringUtils.isBlank(account) || StringUtils.isBlank(pass) ) {
            return result.fail("请填写完整资源字段！");
        }
        Source source = new Source();
        source.setSourceName(sourceName);
        source.setSourceType(sourceType.charAt(0));
        source.setIp(sourceIp);
        source.setAccount(account);
        source.setPass(pass);
        return sourceService.addSource(source);
    }

    @GetMapping("/modifyNickname")
    @ResponseBody
    public Result<Void> modifyNickname(@RequestParam String phone,
                                       @RequestParam String nickname,
                                       @RequestParam String password) {
        Result<Void> result = new Result<>();
        if (StringUtils.isBlank(phone) || StringUtils.isBlank(nickname) || StringUtils.isBlank(password)) {
            return result.fail("请填写完整字段！");
        }
        User user = new User();
        user.setNickname(nickname);
        user.setPhone(phone);
        user.setPassword(password);
        return userService.modifyNickname(user);
    }

    @GetMapping("/modifyPassword")
    @ResponseBody
    public Result<Void> modifyPassword(@RequestParam String phone,
                                       @RequestParam String oldPass,
                                       @RequestParam String newPass) {
        Result<Void> result = new Result<>();
        if (StringUtils.isBlank(phone) || StringUtils.isBlank(oldPass) || StringUtils.isBlank(newPass)) {
            return result.fail("请填写完整字段！");
        }
        return userService.modifyPassword(phone, oldPass, newPass);
    }

    @RequestMapping("/heartbeat")
    @ResponseBody
    public Result<Void> heartbeat(@RequestParam(required = false) String workerName,
                                    @RequestParam(required = false) String port,
                                    HttpServletRequest request) {
        Result<Void> result = new Result<>();

        if (request == null) {
           return result.fail("null_httpServletRequest");
        }
        String ip = RequestUtil.getIpAddr(request);

        if (StringUtils.isNotBlank(port)) {
            // first heartbeat
            NodeDTO nodeDTO = new NodeDTO();
            nodeDTO.setType(NodeType.WORKER.getType());
            nodeDTO.setIp(ip);
            nodeDTO.setPort(Integer.parseInt(port));
            nodeDTO.setName(workerName);
            String name = workerContainer.addWorker(nodeDTO);
            if (name == null) {
                return result.fail("worker_num_out_of_max_value");
            }
            return result.success(name);
        }

        // not first heartbeat
        if (StringUtils.isBlank(workerName)) {
            return result.fail("null_workerName_heartbeat");
        }
        workerContainer.aliveWorker(workerName);
        return result.success();
    }
}
