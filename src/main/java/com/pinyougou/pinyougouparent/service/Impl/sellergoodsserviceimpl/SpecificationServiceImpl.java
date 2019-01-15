package com.pinyougou.pinyougouparent.service.Impl.sellergoodsserviceimpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.pinyougouparent.entity.PageResult;
import com.pinyougou.pinyougouparent.mapper.TbSpecificationMapper;
import com.pinyougou.pinyougouparent.mapper.TbSpecificationOptionMapper;
import com.pinyougou.pinyougouparent.pojo.TbSpecification;
import com.pinyougou.pinyougouparent.pojo.TbSpecificationExample;
import com.pinyougou.pinyougouparent.pojo.TbSpecificationOption;
import com.pinyougou.pinyougouparent.pojo.TbSpecificationOptionExample;
import com.pinyougou.pinyougouparent.service.sellergoods.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import com.pinyougou.pinyougouparent.pojo.pojogroup.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
@Service
@Transactional
public class SpecificationServiceImpl implements SpecificationService {
    @Autowired
    private TbSpecificationMapper specificationMapper;
    @Autowired
    private TbSpecificationOptionMapper specificationOptionMapper;
    @Override
    public List<TbSpecification> findAll() {
        return specificationMapper.selectByExample(null);
    }

    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<TbSpecification> page=   (Page<TbSpecification>) specificationMapper.selectByExample(null);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public void add(Specification specification) {
//获取规格实体
        TbSpecification tbspecification = specification.getSpecification();
        specificationMapper.insert(tbspecification);

        //获取规格选项集合
        List<TbSpecificationOption> specificationOptionList = specification.getSpecificationOptionList();
        for( TbSpecificationOption option:specificationOptionList){
            option.setSpecId(tbspecification.getId());//设置规格ID
            specificationOptionMapper.insert(option);//新增规格
        }
    }

    @Override
    public void update(Specification specification) {
//获取规格实体
        TbSpecification tbspecification = specification.getSpecification();
        specificationMapper.updateByPrimaryKey(tbspecification);

        //删除原来规格对应的规格选项

        TbSpecificationOptionExample example=new TbSpecificationOptionExample();
        com.pinyougou.pinyougouparent.pojo.TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
        criteria.andSpecIdEqualTo(tbspecification.getId());
        specificationOptionMapper.deleteByExample(example);

        //获取规格选项集合
        List<TbSpecificationOption> specificationOptionList = specification.getSpecificationOptionList();
        for( TbSpecificationOption option:specificationOptionList){
            option.setSpecId(tbspecification.getId());//设置规格ID
            specificationOptionMapper.insert(option);//新增规格
        }
    }

    @Override
    public Specification findOne(Long id) {
        Specification specification=new Specification();
        //获取规格实体
        TbSpecification tbSpecification = specificationMapper.selectByPrimaryKey(id);
        specification.setSpecification(tbSpecification);

        //获取规格选项列表

        TbSpecificationOptionExample example=new TbSpecificationOptionExample();
        com.pinyougou.pinyougouparent.pojo.TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
        criteria.andSpecIdEqualTo(id);
        List<TbSpecificationOption> specificationOptionList = specificationOptionMapper.selectByExample(example);

        specification.setSpecificationOptionList(specificationOptionList);

        return specification;//组合实体类
    }

    @Override
    public void delete(Long[] ids) {
        for(Long id:ids){
            //删除规格表数据
            specificationMapper.deleteByPrimaryKey(id);

            //删除规格选项表数据
            TbSpecificationOptionExample example=new TbSpecificationOptionExample();
            com.pinyougou.pinyougouparent.pojo.TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
            criteria.andSpecIdEqualTo(id);
            specificationOptionMapper.deleteByExample(example);
        }
    }

    @Override
    public PageResult findPage(TbSpecification specification, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        TbSpecificationExample example=new TbSpecificationExample();
        TbSpecificationExample.Criteria criteria = example.createCriteria();

        if(specification!=null){
            if(specification.getSpecName()!=null && specification.getSpecName().length()>0){
                criteria.andSpecNameLike("%"+specification.getSpecName()+"%");
            }

        }

        Page<TbSpecification> page= (Page<TbSpecification>)specificationMapper.selectByExample(example);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public List<Map> selectOptionList() {
        // TODO Auto-generated method stub
        return specificationMapper.selectOptionList();
    }
}
