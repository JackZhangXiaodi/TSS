package com.baizhi.controller;
import com.baizhi.Index.SearchIndex;
import com.baizhi.entity.Poetry;
import com.baizhi.service.PoetryService;
import com.baizhi.util.PageIndex;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/poetry")
public class PoetryController {
    //private static String artical;
    @Autowired
    private PoetryService poetryService;
    //检测索引
    @Autowired
    private SearchIndex searchIndex;
    @RequestMapping("/showAll")
    public String showAll(String art,Model model,Integer pageNow) throws IOException, InvalidTokenOffsetsException, ParseException {
        //List<Poetry> all = poetryService.findAll();
        if(pageNow==null || pageNow<1){
            pageNow=1;
        }
        model.addAttribute("art",art);
        System.out.println(pageNow);
        model.addAttribute("page",pageNow);
        Integer pageSize =10;
        //检索数据
        PageIndex pageIndex = searchIndex.all(art, pageNow, pageSize);
        List<Poetry> poetries = pageIndex.getPoetries();
        if(poetries!=null){
//        总条数
            Integer total = pageIndex.getTotal();
//        总页数
            int count = total/pageSize;
            model.addAttribute("counts",count);
            model.addAttribute("poetry",poetries);
            return "showAll";

        }else{
            return "redirect:/search.jsp";
        }
    }
}
