package com.hust.springbootmybatisquickstart.controller;

import com.hust.springbootmybatisquickstart.mapper.UserdataMapper;
import com.hust.springbootmybatisquickstart.pojo.*;

import com.sun.mail.util.MailSSLSocketFactory;

import org.springframework.format.annotation.DateTimeFormat;

import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.UUID;

@RestController//会将数据渲染为json格式的数据
public class UserController{
    @Resource
    private UserdataMapper userdataMapper;

    @PostMapping("/super/users")
    public String addUser(@RequestBody Noindexuserdata noindexuserdata) {
        try {
            if (noindexuserdata.getName().matches("[\\u4e00-\\u9fa5]{2,15}")) {
                // 如果匹配成功的处理逻辑
                System.out.println("姓名格式正确");
            }
            else throw new Abnormal("姓名格式不正确");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("正则表达式语法错误: " + e.getMessage());
            return "name was illegal,your name was " + noindexuserdata.getName();
            // 在这里进行异常处理，例如输出错误信息或执行其他操作
        }
        try {
            if (noindexuserdata.getName().matches("([1-6][1-9]|50)\\\\d{4}(18|19|20)\\\\d{2}((0[1-9])|10|11|12)(([0-2][1-9])|10|20|30|31)\\\\d{3}[0-9Xx]")) {
                // 如果匹配成功的处理逻辑
                System.out.println("id格式正确");
            }
            else throw new Abnormal("id格式不正确");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("正则表达式语法错误: " + e.getMessage());
            return "id was illegal,your id was " + noindexuserdata.getId();
            // 在这里进行异常处理，例如输出错误信息或执行其他操作
        }
        try {
            if (noindexuserdata.getName().matches("1[3-9]\\\\d{9}|0\\\\d{2,7}-?[1-9]\\\\d{4,19}")) {
                // 如果匹配成功的处理逻辑
                System.out.println("电话的格式正确");
            }
            else throw new Abnormal("电话格式不正确");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("正则表达式语法错误: " + e.getMessage());
            return "phone was illegal,your phone was " + noindexuserdata.getPhone();
            // 在这里进行异常处理，例如输出错误信息或执行其他操作
        }
        try {
            if (noindexuserdata.getName().matches("(北京|天津|上海|重庆|河北|山西|内蒙古|辽宁|吉林|黑龙江|江苏|浙江|安徽" +
                    "|福建|江西|山东|河南|湖北|湖南|广东|海南|四川|贵州|云南|西藏|陕西|甘肃|青海|宁夏|新疆|台湾|香港|澳门)" +
                    "(\\\\u4e00-\\\\u9fff{0,10}(省?|自治区?|行政区?|市?))" +
                    "|(\\\\u4e00-\\\\u9fff{0,10}(市?|自治州?|地区?|行政单位?|盟?|市辖区?|县?))")) {
                // 如果匹配成功的处理逻辑
                System.out.println("地址的格式正确");
            }
            else throw new Abnormal("地址格式不正确");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("正则表达式语法错误: " + e.getMessage());
            return "address was illegal,your address was " + noindexuserdata.getAddress();
            // 在这里进行异常处理，例如输出错误信息或执行其他操作
        }
        userdataMapper.add(noindexuserdata);
        return "ok,添加user成功";
    }

    @GetMapping("/normal/users")
    public List<Userdata> searchUser(Integer index, String name, String id, String phone, String address) {
        List<Userdata> list = userdataMapper.list(index, name, id, phone, address);
        System.out.println(list);
        return list;
    }

    @GetMapping("/normal/cargo_weights")
    public Integer sum(@RequestParam String action, @RequestParam String portName, @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate, @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        Integer sum = userdataMapper.getTotalCargoWeight(action, portName, startDate, endDate);
        System.out.println(sum);
        return sum;
    }

    @GetMapping("/normal/variations")
    public List<Container> variation(String portName, @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate, @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate, String target, String action) {
        List<Container> list = userdataMapper.list2(portName, startDate, endDate, target, action);
        System.out.println(list);
        return list;
    }

    @GetMapping("/normal/proportions")
    public String proportion(String portName, @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate, @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate, String target, String action, String cargoName) {
        Integer allSum = userdataMapper.getTotalCargoWeight(action, portName, startDate, endDate);
        Integer oneSum = userdataMapper.CargoWeightProportion(action, portName, startDate, endDate, cargoName);
        double proportion = (double) oneSum / allSum * 100;
        return String.format("%.2f%%", proportion);
    }

    @GetMapping("/normal/directions")
    public List<Direction> cargoDirection(String target, @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate, @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate, String action) {
        List<Direction> list = userdataMapper.list3(target, startDate, endDate, action);
        System.out.println(list);
        return list;
    }

    @GetMapping("/normal/orders")
    public List<Company> userOrder(String name) {
        List<Company> list = userdataMapper.list4(name);
        System.out.println(list);
        return list;
    }

    @GetMapping("/normal/boats")
    public List<Boat> boatDetail(String shipCompany, @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate, @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        List<Boat> list = userdataMapper.list5(shipCompany, startDate, endDate);
        System.out.println(list);
        return list;
    }

    @PutMapping("/super/updates")
    public String update(@RequestBody Updateuser updateuser) {
        userdataMapper.update1(updateuser.getCargoName(), updateuser.getCargoWeight(), updateuser.getIndex());
        userdataMapper.update2(updateuser.getPortName(), updateuser.getDepartPart(),
                updateuser.getArrivePart(), updateuser.getLoadStartTime(),
                updateuser.getLoadEndTime(), updateuser.getDepartTime(), updateuser.getArriveTime(), updateuser.getLadingId());
        userdataMapper.update3(updateuser.getPortName(), updateuser.getDepartPart(), updateuser.getArrivePart(), updateuser.getUnloadStartTime(),
                updateuser.getUnloadEndTime(), updateuser.getDepartTime(), updateuser.getArriveTime(), updateuser.getLadingId());
        userdataMapper.update4(updateuser.getActionDate(), updateuser.getPort(), updateuser.getContainerId(), updateuser.getAction());
        return "ok";
    }


    @DeleteMapping("/super/deletes")
    public String delete(@RequestBody Delectuser delectuser) {
        userdataMapper.delete1(delectuser.getIndex());
        userdataMapper.delete2(delectuser.getLadingId());
        userdataMapper.delete3(delectuser.getLadingId());
        Integer num = delectuser.getNumber();
        for (; num > 0; num--)
            userdataMapper.delete4(delectuser.getLadingId());
        return "ok";
    }

    @PostMapping("/super/adds")
    public String adding(@RequestBody Adduser adduser) {
        userdataMapper.updateLoad(adduser.getShipCompany(),adduser.getShipName(),adduser.getLoadStartTime(),adduser.getLoadEndTime(),adduser.getDepartTime(),adduser.getArriveTime(),
        adduser.getPortName(),adduser.getLadingId(),adduser.getContainerId(),adduser.getContainerSize(),adduser.getDepartPart(),adduser.getArrivePart());
        userdataMapper.updateUnload(adduser.getShipCompany(),adduser.getShipName(),adduser.getUnloadStartTime(),adduser.getUnloadEndTime(),adduser.getDepartTime(),adduser.getArriveTime(),
        adduser.getPortName(),adduser.getLadingId(),adduser.getContainerId(),adduser.getContainerSize(),adduser.getDepartPart(),adduser.getArrivePart());
        Integer nums = adduser.getNumber();/*搞半天原来是命名的问题，无语了，我的一早上*/
        System.out.println(nums);/*还是得用sout来进行错误勘察，当然用日志也行*/
        Integer all = 2*nums;
        for (int i = 0; i < all; i++) {
            userdataMapper.updateContainer(adduser.getPortName(), adduser.getContainerId(), adduser.getContainerSize(),
                    adduser.getLadingId(), adduser.getContainerYard()[i], adduser.getAction()[i],
                    LocalDate.parse(adduser.getActionDate()[i], DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
        userdataMapper.updateLogistics(adduser.getLadingId(),adduser.getOwnerName(),adduser.getOwnerId(),adduser.getLogisticsCom(),
                adduser.getContainerId(),adduser.getCargoName(),adduser.getCargoWeight());
        return "ok";
    }
/*    根据错误信息可以看出，你执行的 SQL 插入语句违反了数据库唯一性约束，即 email 列上设置了唯一性索引，但在插入数据时却插入了相同值 '123456@hust.com'。
        注意事项：用户名不可以重名，邮箱不可以搞一样的（要是搞一样的，程序会崩溃）
    你可以通过检查你的代码，确认是否存在重复插入数据的情况。如果你确定没有重复插入数据，那么可能是你的数据表中已经存在相同的 email 数据了。*/
private Map<String, Long> lastRequestTimeMap = new ConcurrentHashMap<>();

    @PostMapping("/enrolls")
    public String enrolling (@RequestBody Trueuser trueuser){

        /*加密的话就先拦截一下，处理啊完了再让它进行存储*/

        String username = trueuser.getUsername();
        // 从缓存中取出该用户最后一次请求邮件的时间戳
        Long lastRequestTime = lastRequestTimeMap.get(username);
        // 判断当前时间和上一次请求邮件的时间间隔是否超过设定的阈值
        if (lastRequestTime != null && System.currentTimeMillis() - lastRequestTime < 60_000) {
            // 如果未超过阈值，则返回提示信息拒绝发送邮件
            return "请求过于频繁，请稍后再试";
        }
        // 更新缓存中该用户的时间戳为当前时间
        lastRequestTimeMap.put(username, System.currentTimeMillis());


        Properties prop = new Properties();
        prop.setProperty("mail.host","smtp.qq.com"); //设置QQ邮件服务器
        prop.setProperty("mail.transport.protocol","smtp"); //邮件发送协议
        prop.setProperty("mail.smtp.auth","true"); //需要验证用户名密码
        //关于QQ邮箱，还要设置SSL加密，加上以下代码即可
        MailSSLSocketFactory sf = null;
        try {
            sf = new MailSSLSocketFactory();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        sf.setTrustAllHosts(true);
        prop.put("mail.smtp.ssl.enable","true");
        prop.put("mail.smtp.ssl.socketFactory",sf);
        //使用JavaMail发送邮件的5个步骤
        //1、创建定义整个应用程序所需的环境信息的Session对象
        Session session = Session.getDefaultInstance(prop, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                //发件人邮件用户名、授权码
                return new PasswordAuthentication("","");
            }
        });
        //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
        session.setDebug(true);
        //2、通过session得到transport对象
        Transport ts = null;
        try {
            ts = session.getTransport();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        //3、使用邮箱的用户名和授权码连上邮件服务器
        try {
            ts.connect("smtp.qq.com","","");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        //4、创建邮件：写邮件
        //注意需要传递Session
        MimeMessage message = new MimeMessage(session);
        //指明邮件的发件人 24736743
        try {
            message.setFrom(new InternetAddress(""));
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        //指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
        try {
            message.setRecipient(Message.RecipientType.TO,new InternetAddress(trueuser.getEmail()));
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        //邮件的标题
        try {
            message.setSubject("以下是注册验证");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        //邮件的文本内容
        try {
            message.setContent("<h1 style='color:red'>验证成功，您已经成功注册！</h1>","text/html;charset=UTF-8");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        //5、发送邮件
        try {
            ts.sendMessage(message,message.getAllRecipients());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        //6、关闭连接
        try {
            ts.close();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        try {
            if (trueuser.getEmail().matches("([a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6})")) {
                // 如果匹配成功的处理逻辑
                System.out.println("email格式正确");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("正则表达式语法错误: " + trueuser.getEmail());
            return "email was illegal,your email was " +trueuser.getEmail();
            // 在这里进行异常处理，例如输出错误信息或执行其他操作
        }
    String password=trueuser.getPassword();
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] messageDigest = md.digest(password.getBytes());
        // Convert byte array to signum representation
        StringBuilder sb = new StringBuilder();
        for (byte b : messageDigest) {
            sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }
        userdataMapper.enroll(trueuser.getUsername(),sb.toString(),trueuser.getEmail());
        return "您的账号已经注册成功";
    }
    @DeleteMapping("/logouts")
    public String logouting(@RequestBody Trueuser trueuser){
        String password=trueuser.getPassword();
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] messageDigest = md.digest(password.getBytes());
        // Convert byte array to signum representation
        StringBuilder sb = new StringBuilder();
        for (byte b : messageDigest) {
            sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }
        if(userdataMapper.verify(trueuser.getUsername(),sb.toString())==1){
            userdataMapper.logout(trueuser.getUsername(),sb.toString());/*累了，搞半天是之前误删了，然后忘记加上去了，虽然是成功了显示控制台成功删除，但是其实delete语句没有执行*/
            return "您已成功删除账号";
        }
        else return "删除失败，请检查你的昵称与密码是否正确";
    }


    @PostMapping("/verifys")
    public String verifying(@RequestBody Trueuser trueuser) {
        String password = trueuser.getPassword();
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] messageDigest = md.digest(password.getBytes());
        // Convert byte array to signum representation
        StringBuilder sb = new StringBuilder();
        for (byte b : messageDigest) {
            sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }//搞半天才发现我的这两个参数传反了，😓无语了
        if (userdataMapper.verify(trueuser.getUsername(), sb.toString()) == 1) {
            return "您已成功登录账号";
        } else return "登录失败，请检查你的昵称与密码是否正确";
    }

}



