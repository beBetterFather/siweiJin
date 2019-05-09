package com.app.modules.web;

import com.app.modules.entity.FileInfo;
import com.app.modules.entity.Student;
import com.app.modules.service.intf.IntfFileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private IntfFileInfo fileInfo;

    @RequestMapping("/hello")
    public String hello(Model model){
        List<Student> list=new ArrayList<>();
        list.add(new Student("张三", 20, "北京"));
        list.add(new Student("李四", 30, "上海"));
        list.add(new Student("王五", 40, "河北"));
        list.add(new Student("赵六", 50, "山西"));
        model.addAttribute("list", list);
        return "/index";
    }

    @RequestMapping("/mybatis")
    public String mybatis(Model model){
        List<FileInfo> fileInfos = fileInfo.queryAll();
        model.addAttribute("list", fileInfos);
        return "/mybatis";
    }
}

