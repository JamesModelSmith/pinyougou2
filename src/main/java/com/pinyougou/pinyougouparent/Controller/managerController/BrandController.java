package com.pinyougou.pinyougouparent.Controller.managerController;

import com.pinyougou.pinyougouparent.entity.PageResult;
import com.pinyougou.pinyougouparent.entity.Result;
import com.pinyougou.pinyougouparent.pojo.TbBrand;
import com.pinyougou.pinyougouparent.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/brand")
public class BrandController {
    private final BrandService brandService;

    @Autowired
    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @RequestMapping("/findAll")
    @ResponseBody
    public List<TbBrand> findAll(){

       return brandService.findAll();
    }
    @RequestMapping("/find")
    @ResponseBody
    public String Test(){
        return "白璐啊";
    }
    /**
     * 获取登录界面
     * @return
     */
   // @GetMapping("/login")
    //public String login() {
       // return "login";
   // }

    @RequestMapping("/findPage")
    @ResponseBody
    public PageResult findPage(int page,int size){
        return brandService.findPage(page,size);
    }

    @RequestMapping("/add")
    public Result add(@RequestBody TbBrand brand){
      try{
          brandService.add(brand);
          return new Result(true,"新增成功");
      }catch (Exception e){
       e.printStackTrace();
       return new Result(false,"新增失败");
      }
    }
    @RequestMapping("/findOne")
    public TbBrand findOne(Long id){
        return brandService.findOne(id);
  }
    @RequestMapping("/update")
    public Result update(@RequestBody TbBrand brand){
        try{
            brandService.update(brand);
            return new Result(true,"修改成功");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"修改失败");
        }
    }


    @RequestMapping("/delete")
    public Result update(Long [] ids){
        try{
            brandService.delete(ids);
            return new Result(true,"删除成功");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"删除失败");
        }
    }
    @RequestMapping("/search")
    public PageResult search(@RequestBody TbBrand brand,int page,int size){
        return brandService.findPage(brand,page,size);
    }
     @RequestMapping("/selectOptionList")
    public List<Map> selectOptionList(){
        return brandService.selectOptionList();
    }
}
