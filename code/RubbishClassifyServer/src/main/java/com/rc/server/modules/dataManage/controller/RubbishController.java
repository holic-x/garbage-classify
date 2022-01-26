package com.rc.server.modules.dataManage.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rc.server.framework.utils.CommonUtil;
import com.rc.server.framework.utils.PageHelper;
import com.rc.server.framework.utils.Res;
import com.rc.server.modules.dataManage.model.Rubbish;
import com.rc.server.modules.dataManage.service.RubbishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;

@RestController
@RequestMapping("/dataManage/rubbish")
public class RubbishController {

    @Autowired
    private RubbishService rubbishService;

    @Value("${custom.common.uploadPath}")
    private String uploadPath;

    public static File getImgDirFile(String targetPath) {
        // 构建上传文件的存放 "文件夹" 路径
        String fileDirPath = new String("src/main/resources" + targetPath);

        File fileDir = new File(fileDirPath);
        if (!fileDir.exists()) {
            // 递归生成文件夹
            fileDir.mkdirs();
        }
        return fileDir;
    }

    /**
     * 保存信息
     **/
//    @RequestMapping("/edit")
//    public Res save(@RequestBody Rubbish rubbish) {
//        rubbishService.edit(rubbish);
//        return Res.ok();
//    }

    @RequestMapping(value = {"/save","/update"})
    public Res save(@RequestParam("uploadFile") MultipartFile file,
                    @RequestParam("rubbishId") String rubbishId,
                    @RequestParam("rubbishCode") String rubbishCode,
                    @RequestParam("rubbishName") String rubbishName,
                    @RequestParam("classifyId") String classifyId,
                    @RequestParam("oldPicUrl") String oldPicUrl,
                    @RequestParam("remark") String remark,
                    HttpServletRequest req) throws Exception{

        // 封装实体类
        Rubbish rubbish = new Rubbish();

        // 该方式存储的是容器路径，需对应调整为指向当前目录的路径
        if(file!=null){
            // 拿到文件名
            String fileName = file.getOriginalFilename();
            // 随机生成uuid作为文件名 file.getOriginalFilename().split("\\.")
            String newFileName = CommonUtil.getRandomId() + fileName.substring(fileName.lastIndexOf("."),fileName.length());
            // 存放上传图片的文件夹
            File fileDir = getImgDirFile(uploadPath);
            // 输出文件夹绝对路径  -- 这里的绝对路径是相当于当前项目的路径而不是“容器”路径
            System.out.println(fileDir.getAbsolutePath());
            // 构建真实的文件路径
            File newFile = new File(fileDir.getAbsolutePath() + "/" + newFileName);
            System.out.println(newFile.getAbsolutePath());
            // 上传图片到 -》 “绝对路径”
            file.transferTo(newFile);
            // 存储文件名称，后续系统自动拼接完整路径 // /xxxx/** 对应前端项目相对路径前缀（此处指定完整路径）
            String requestUrl = req.getScheme() //当前链接使用的协议
                    +"://" + req.getServerName()//服务器地址
                    + ":" + req.getServerPort() //端口号
                    + req.getContextPath();// 应用名称，如果应用名称为
//                    + req.getServletPath() //请求的相对url
//                    + (StrUtils.isBlank(req.getQueryString())?"":("?"+req.getQueryString())); //请求参数

//            rubbish.setRubbishPic("http://localhost:8080"+"/rc-server" + uploadPath + "/" + newFileName);
            rubbish.setRubbishPic(requestUrl + uploadPath + "/" + newFileName);
        }else{
            // 没有检测到更新文件，引用原有的旧文件路径
            rubbish.setRubbishPic(oldPicUrl);
        }

        rubbish.setRubbishId(rubbishId);
        rubbish.setRubbishCode(rubbishCode);
        rubbish.setRubbishName(rubbishName);
        rubbish.setClassifyId(classifyId);
        rubbish.setRemark(remark);
        rubbishService.edit(rubbish);

        return Res.ok();
    }








    /**
     * 删除信息(单条或批量删除)
     **/
    @RequestMapping("/delete")
    public Res delete(@RequestBody JSONObject requestParam) {
        rubbishService.delete(requestParam.getObject("rubbishIdList", ArrayList.class));
        return Res.ok();
    }

    /**
     * list信息
     **/
    @RequestMapping("/list")
    // @RequestBody HashMap<String, String> map 转化JSONObject.parseObject(JSON.toJSONString(map))
    public Res list(@RequestBody JSONObject queryCond ) {
        Page<Rubbish> pageData = rubbishService.getByPage(queryCond);
        // 借助分页插件转化分页数据
        return Res.ok().put("page",new PageHelper<Rubbish>(pageData));
    }

    /**
     * 获取信息
     **/
    @RequestMapping("/info/{rubbishId}")
    public Res info(@PathVariable String rubbishId) {
        Rubbish rubbish = rubbishService.getById(rubbishId);
        return rubbish!=null?Res.ok().put("rubbish",rubbish):Res.error("指定ID关联信息不存在");
    }

}
