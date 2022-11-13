package org.zjl.staff.controller;

import lombok.val;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.zjl.staff.entity.Wages;
import org.zjl.staff.service.WagesService;

import javax.annotation.Resource;

/**
 * (Wages)表控制层
 * @since 2022-11-12 16:38:24
 */
@Controller
@RequestMapping("/")
public class WagesController {
    /**
     * 服务对象
     */
    @Resource
    private WagesService wagesService;

    /**
     * 查询
     * @param wages 筛选条件
     * @return 查询结果
     */
    @GetMapping
    public String queryAll(Model model, Wages wages) {
        val list= wagesService.queryAll(wages);
        model.addAttribute("list",list);
        model.addAttribute("wages",wages);
        Double sum=0D;
        for (Wages wages1 : list) {
            sum+=Double.parseDouble(wages1.getMoney());
        }
        model.addAttribute("sum",sum);
        return "testServer";
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/wages/{id}")
    @ResponseBody
    public Wages queryById(@PathVariable("id") Integer id) {
        return this.wagesService.queryById(id);
    }

    /**
     * 新增数据
     *
     * @param wages 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public String add(Wages wages) {
        wagesService.insert(wages);
        return "redirect:/";
    }
    @PostMapping("/quickAdd")
    public String quickAdd(@RequestParam String settlementTime,@RequestParam String targetTime) {
        wagesService.insertQuick(settlementTime,targetTime);
        return "redirect:/";
    }
    @GetMapping("/queryByName/{name}")
    @ResponseBody
    public Wages quickAdd(@PathVariable String name) {
        return wagesService.getByName(name);
    }
    /**
     * 编辑数据
     *
     * @param wages 实体
     * @return 编辑结果
     */
    @PostMapping("/edit")
    public String edit(Wages wages) {
        this.wagesService.update(wages);
        return "redirect:/";
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Integer id) {
        this.wagesService.deleteById(id);
        return "redirect:/";
    }
    @GetMapping("/export")
    @ResponseBody
    public void queryAll(Wages wages) {
        wagesService.export(wages);
    }
}

