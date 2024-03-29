package org.webmaple.admin.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.apache.commons.collections.CollectionUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.webmaple.admin.Questions;
import org.webmaple.admin.container.BasicDataContainer;
import org.webmaple.admin.container.WorkerContainer;
import org.webmaple.admin.model.Message;
import org.webmaple.admin.model.Source;
import org.webmaple.admin.model.User;
import org.webmaple.admin.service.MessageService;
import org.webmaple.common.enums.MessageType;
import org.webmaple.common.enums.NodeType;
import org.webmaple.common.enums.SourceType;
import org.webmaple.common.model.DataTableDTO;
import org.webmaple.common.model.NodeDTO;
import org.webmaple.common.model.Result;
import org.webmaple.common.network.RequestSender;
import org.webmaple.common.util.RequestUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.webmaple.admin.service.ComponentService;
import org.webmaple.admin.service.SourceService;
import org.webmaple.admin.service.UserService;
import org.webmaple.common.view.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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
    private MessageService messageService;

    @Resource
    private WorkerContainer workerContainer;

    @Resource
    private RequestSender requestSender;

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

    @RequestMapping("/message")
    public String message() {
        return "message";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/commander")
    public String commander(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if ("user".equals(cookie.getName())) {
                String phone = cookie.getValue();
                User user = userService.searchUser(phone).getModel();
                if (user.getAuth() == '0') {
                    return "commander";
                }
            }
        }
        return "error";
    }

    @RequestMapping("/userList")
    @ResponseBody
    public DataTableDTO userList() {
        List<User> userList = userService.userList().getModel();
        for (User user : userList) {
            user.setAuth(user.getAuth() == '0' ? '是' : '否');
        }
        DataTableDTO dataTableDTO = new DataTableDTO();
        dataTableDTO.setCode(0);
        dataTableDTO.setMsg("");
        dataTableDTO.setCount((CollectionUtils.isEmpty(userList)) ? 0 : userList.size());
        dataTableDTO.setData(userList);
        return dataTableDTO;
    }

    @PostMapping("/doLogin")
    @ResponseBody
    public Result<Void> doLogin(@RequestParam String phone,
                                @RequestParam String password,
                                HttpServletResponse response) {
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
            Cookie cookie = new Cookie("user", phone);
            cookie.setMaxAge(8 * 60 * 60); // 设置8小时过期
            response.addCookie(cookie);
        }
        return result;
    }

    @RequestMapping("/exit")
    @ResponseBody
    public Result<Void> exit(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (!"user".equals(cookie.getName())) {
                response.addCookie(cookie);
            }
        }
        return new Result<Void>().success("退出登录成功");
    }

    @PostMapping("/register")
    @ResponseBody
    public Result<Void> register(@RequestParam String phone,
                                 @RequestParam String nickname,
                                 @RequestParam String password,
                                 @RequestParam(required = false) Character question,
                                 @RequestParam(required = false) String answer) {
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
        if (question != null) {
            user.setQuestion(question);
            user.setAnswer(answer);
        }
        return userService.register(user);
    }

    @GetMapping("/getUserInfo")
    @ResponseBody
    public Result<User> getUserInfo(@RequestParam String phone) {
        Result<User> result = new Result<>();
        if (StringUtils.isBlank(phone)) {
            return result.fail("登录状态异常，无法获取用户信息");
        }
        return userService.searchUser(phone);
    }

    @GetMapping("/getUserQuestion")
    @ResponseBody
    public Result<String> getUserQuestion(@RequestParam String phone) {
        Result<String> result = new Result<>();

        User user = userService.searchUser(phone).getModel();
        if (user == null) {
            return result.fail("不存在此用户", null);
        }
        Character question = user.getQuestion();
        if (question == null) {
            return result.success("未设置密保问题", "");
        }
        return result.success("", Questions.questions.get(question));
    }

    @PostMapping("/findPassword")
    @ResponseBody
    public Result<String> findPassword(@RequestParam String phone,
                                       @RequestParam String nickname,
                                       @RequestParam String answer) {
        Result<String> result = new Result<>();

        User user = userService.searchUser(phone).getModel();
        if (user == null) {
            return result.fail("不存在此用户", null);
        }
        if (StringUtils.isNotBlank(nickname) && answer == null) {
            return result.fail("昵称与密保问题至少应填写一个", null);
        }
        if (StringUtils.isNotBlank(nickname) && !nickname.equals(user.getNickname())) {
            return result.fail("昵称错误，找回失败", null);
        }
        if (user.getQuestion() != null && !answer.equals(user.getAnswer())) {
            return result.fail("密保问题答案错误，找回失败", null);
        }
        return result.success("找回成功！", user.getPassword());
    }

    @GetMapping("/searchUserAuth")
    @ResponseBody
    public Result<UserSourceView> searchUserAuth(@RequestParam String phone) {
        Result<UserSourceView> result = new Result<>();
        List<Source> sourceList;
        List<Source> sources;

        Result<User> user = userService.searchUser(phone);
        if (user == null) {
            return result.fail("该用户不存在！");
        }
        UserSourceView userSourceView = new UserSourceView();

        List<String> authValues = new ArrayList<>();
        List<SourceAuthView> authViews;

        if (user.getModel().getAuth() == '0') {
            // 如果是管理员账号
            userSourceView.setCommander(true);
            sourceList = sourceService.querySources().getModel();

            AtomicInteger idx = new AtomicInteger(1);
            authViews = sourceList.stream().map(userSource -> {
                SourceAuthView sourceAuthView = new SourceAuthView();
                sourceAuthView.setAuth(true);
                authValues.add(sourceAuthView.getValue());
                sourceAuthView.setValue(String.valueOf(idx.getAndIncrement()));

                sourceAuthView.setTitle(userSource.getSourceName());
                sourceAuthView.setIp(userSource.getIp());
                try {
                    sourceAuthView.setStype(SourceType.getTypeStr(userSource.getSourceType()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                sourceAuthView.setUser(userSource.getAccount());
                sourceAuthView.setPassword(userSource.getPass());
                sourceAuthView.setType(userSource.getSourceType() == '0' ? "server" : "db");
                return sourceAuthView;
            }).collect(Collectors.toList());

        } else {
            sources = sourceService.querySources().getModel();
            sourceList = sourceService.queryUserSources(phone).getModel();
            if (CollectionUtils.isEmpty(sourceList)) {
                return result.fail("该用户不存在！");
            }

            HashSet<String> titleSet = new HashSet<>();
            AtomicInteger idx = new AtomicInteger(1);
            authViews = sourceList.stream().map(userSource -> {
                SourceAuthView sourceAuthView = new SourceAuthView();
                sourceAuthView.setAuth(true);
                sourceAuthView.setValue(String.valueOf(idx.getAndIncrement()));
                authValues.add(sourceAuthView.getValue());
                titleSet.add(userSource.getSourceName());

                sourceAuthView.setTitle(userSource.getSourceName());
                sourceAuthView.setIp(userSource.getIp());
                try {
                    sourceAuthView.setStype(SourceType.getTypeStr(userSource.getSourceType()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                sourceAuthView.setUser(userSource.getAccount());
                sourceAuthView.setPassword(userSource.getPass());
                sourceAuthView.setType(userSource.getSourceType() == '0' ? "server" : "db");
                return sourceAuthView;
            }).collect(Collectors.toList());

            for (Source source : sources) {
                if (!titleSet.contains(source.getSourceName())) {
                    SourceAuthView sourceAuthView = new SourceAuthView();
                    sourceAuthView.setAuth(false);
                    sourceAuthView.setValue(String.valueOf(idx.getAndIncrement()));
                    sourceAuthView.setTitle(source.getSourceName());
                    sourceAuthView.setIp(source.getIp());
                    try {
                        sourceAuthView.setStype(SourceType.getTypeStr(source.getSourceType()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    sourceAuthView.setType(source.getSourceType() == '0' ? "server" : "db");
                    authViews.add(sourceAuthView);
                }
            }
        }

        userSourceView.setSourceAuthViews(authViews);
        userSourceView.setAuthValues(authValues);
        return result.success(userSourceView);
    }

    @RequestMapping("/applyAuth")
    @ResponseBody
    public Result<Void> applyAuth(@RequestParam String phone, @RequestParam String reason) {
        return messageService.applyAuth(phone, reason);
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

    @GetMapping("/basicData")
    @ResponseBody
    public Result<BasicDataView> basicData() {
        Result<BasicDataView> result = new Result<>();

        BasicDataView basicDataView = new BasicDataView();

        basicDataView.setNodeNum(workerContainer.size());
        basicDataView.setSpiderNum(BasicDataContainer.getSpiderNum());
        basicDataView.setTimedJobNum(BasicDataContainer.getTimedJobNum());
        return result.success(basicDataView);
    }

//    @RequestMapping("/spiderAdvance")
//    @ResponseBody
//    public Result<List<SpiderAdvance>> advanceList() {
//        Result<List<SpiderAdvance>> result = new Result<>();
//        List<SpiderAdvance> res = new ArrayList<>();
//
//        List<NodeDTO> workers = workerContainer.getWorkerList();
//
//        try {
//            for (NodeDTO worker : workers) {
//                Request request = RequestUtil.getRequest(worker.getIp(), worker.getPort(), "advance", null);
//                CloseableHttpResponse response = requestSender.request(RequestUtil.getHttpUriRequest(request));
//                String text = RequestUtil.getResponseText(response);
//                JSONArray jsonArray = JSON.parseArray(text);
//
//                for (Object o : jsonArray) {
//                    String advanceStr = (String) o;
//                    SpiderAdvance advance = JSON.parseObject(advanceStr, SpiderAdvance.class);
//                    res.add(advance);
//                }
//            }
//            return result.success(res);
//
//        } catch (Exception e) {
//            return result.fail(e.getMessage());
//        }
//    }

    @RequestMapping("/accuse")
    @ResponseBody
    public Result<Void> accuse(@RequestParam String accuseId,
                               @RequestParam String accuseReason,
                               @RequestParam String userId) {
        if (StringUtils.isBlank(accuseId) || StringUtils.isBlank(accuseReason) || StringUtils.isBlank(userId)) {
            return new Result<Void>().fail("举报信息不能为空！");
        }
        return messageService.accuse(accuseId, accuseReason, userId);
    }

    @RequestMapping("/authUser")
    @ResponseBody
    public Result<Void> authUser(@RequestParam String phone) {
        if (StringUtils.isBlank(phone)) {
            return new Result<Void>().fail("用户id不能为空！");
        }
        return userService.authUser(phone);
    }

    @RequestMapping("/delUser")
    @ResponseBody
    public Result<Void> delUser(@RequestParam String phone) {
        if (StringUtils.isBlank(phone)) {
            return new Result<Void>().fail("用户id不能为空！");
        }
        return userService.delUser(phone);
    }

    @RequestMapping("/messageList")
    @ResponseBody
    public DataTableDTO messageList(@RequestParam String id) {
        if (StringUtils.isBlank(id)) {
            return new DataTableDTO();
        }

        List<Message> messages = messageService.userMessages(id).getModel();
        DataTableDTO dataTableDTO = new DataTableDTO();
        dataTableDTO.setCode(0);
        dataTableDTO.setMsg("");
        dataTableDTO.setCount(messages.size());
        dataTableDTO.setData(messages);
        return dataTableDTO;
    }

    @RequestMapping("/commanderMsgList")
    @ResponseBody
    public CmdMsgListView commanderMsgList(@RequestParam String id) {
        if (StringUtils.isBlank(id)) {
            return new CmdMsgListView();
        }

        List<Message> messages = messageService.commanderMessages(id).getModel();
        CmdMsgListView cmdMsgListView = new CmdMsgListView();

        for (Message message : messages) {
            CommanderMsgView commanderMsgView = new CommanderMsgView();

            commanderMsgView.setContent("用户 " + message.getFromUser() + " :" + message.getContent());
            commanderMsgView.setDate(message.getDate().toString());
            switch (message.getType()) {
                case '0':
                    commanderMsgView.setTitle("【" + MessageType.APPLY.getName() + "】");
                    cmdMsgListView.getNoticeView().add(commanderMsgView);
                    break;
                case '1':
                    commanderMsgView.setTitle("【" + MessageType.ACCUSE.getName() + "】");
                    cmdMsgListView.getAccuseView().add(commanderMsgView);
                    break;
                case '2':
                    commanderMsgView.setTitle("【" + MessageType.OTHER.getName() + "】");
                    cmdMsgListView.getOtherView().add(commanderMsgView);
                    break;
            }
        }
        return cmdMsgListView;
    }

    @PostMapping("/deleteMessage")
    @ResponseBody
    public Result<Void> deleteMessage(@RequestParam List<Message> messages) {
        Result<Void> result = new Result<>();
        if (CollectionUtils.isEmpty(messages)) {
            return result.success("删除成功!");
        }

        messageService.deleteMessages(messages);
        return result.success("删除成功！");
    }

    @RequestMapping("/heartbeat")
    @ResponseBody
    public Result<Void> heartbeat(@RequestParam(required = false) String workerName,
                                    @RequestParam String port,
                                    HttpServletRequest request) {
        Result<Void> result = new Result<>();

        if (request == null) {
           return result.fail("null_httpServletRequest");
        }
        String ip = RequestUtil.getIpAddr(request);

        if (StringUtils.isBlank(workerName)) {
            // first heartbeat
            NodeDTO nodeDTO = new NodeDTO();
            nodeDTO.setType(NodeType.WORKER.getType());
            if (ip == null) {
                return result.fail("worker_ip_is_null");
            }
            nodeDTO.setIp(ip.startsWith("10.") ? "127.0.0.1" : ip);
            nodeDTO.setPort(Integer.parseInt(port));
            nodeDTO.setName(workerName);
            String name = workerContainer.addWorker(nodeDTO);
            if (name == null) {
                return result.fail("worker_num_out_of_max_value");
            }
            return result.success(name);
        }

        // not first heartbeat
        workerContainer.aliveWorker(ip, port, workerName);
        return result.success();
    }
}
