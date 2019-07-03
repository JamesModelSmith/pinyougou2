package com.pinyougou.pinyougouparent.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.pinyougouparent.entity.PageResult;
import com.pinyougou.pinyougouparent.mapper.TbBrandMapper;
import com.pinyougou.pinyougouparent.pojo.TbBrand;
import com.pinyougou.pinyougouparent.pojo.TbBrandExample;
import com.pinyougou.pinyougouparent.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class BrandServiceimpl implements BrandService {

    private final TbBrandMapper brandMapper;

    @Autowired
    public BrandServiceimpl(TbBrandMapper brandMapper) {
        this.brandMapper = brandMapper;
    }

    @Override
    public List<TbBrand> findAll() {
        return brandMapper.selectByExample(null);
    }

    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);//分页
        Page<TbBrand> page=(Page<TbBrand>) brandMapper.selectByExample(null);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void add(TbBrand brand) {
        brandMapper.insert(brand);
    }

    @Override
    public TbBrand findOne(Long id) {
        return brandMapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(TbBrand brand) {
       brandMapper.updateByPrimaryKey(brand);
    }

    @Override
    public void delete(Long[] ids) {
        for(Long id:ids){
            brandMapper.deleteByPrimaryKey(id);
        }
    }

    @Override
    public PageResult findPage(TbBrand brand, int pageNum, int pageSize) {

        PageHelper.startPage(pageNum,pageSize);//分页
        //自定义查询条件
        TbBrandExample example=new TbBrandExample();

        TbBrandExample.Criteria criteria=example.createCriteria();
        if(brand!=null){
           if(brand.getName()!=null&&brand.getName().length()>0){
               criteria.andNameLike("%"+brand.getName()+"%");
           }
           if(brand.getFirstChar()!=null&&brand.getFirstChar().length()>0){
               criteria.andFirstCharLike("%"+brand.getFirstChar()+"%");
           }
        }

        Page<TbBrand> page=(Page<TbBrand>)brandMapper.selectByExample(example);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public List<Map> selectOptionList() {
        return brandMapper.selectOptionList();
    }
}
