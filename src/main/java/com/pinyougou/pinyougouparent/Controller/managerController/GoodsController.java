package com.pinyougou.pinyougouparent.Controller.managerController;
import java.util.List;

import com.pinyougou.pinyougouparent.entity.PageResult;
import com.pinyougou.pinyougouparent.entity.Result;
import com.pinyougou.pinyougouparent.pojo.TbGoods;
import com.pinyougou.pinyougouparent.pojo.TbItem;
import com.pinyougou.pinyougouparent.pojo.pojogroup.Goods;
import com.pinyougou.pinyougouparent.service.pageService.ItemPageService;
import com.pinyougou.pinyougouparent.service.sellergoods.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/goods")
public class GoodsController {

	@Autowired
	private GoodsService goodsService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbGoods> findAll(){
		return goodsService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult findPage(int page, int rows){
		return goodsService.findPage(page, rows);
	}
	
	/**
	 * 增加
	 * @param goods
	 * @return
	 */
	@RequestMapping("/add")
	public Result add(@RequestBody Goods goods){
		//获取商家ID
		String sellerId= SecurityContextHolder.getContext().getAuthentication().getName();
		goods.getGoods().setSellerId(sellerId);//设置商家ID

		try {
			goodsService.add(goods);
			return new Result(true, "增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "增加失败");
		}
	}
	
	/**
	 * 修改
	 * @param goods
	 * @return
	 */
	@RequestMapping("/update")
	public Result update(@RequestBody Goods goods){
		//当前商家ID
		String sellerId=SecurityContextHolder.getContext().getAuthentication().getName();
		//判断当前商品是否为当前商家的商品
		Goods goods2=goodsService.findOne(goods.getGoods().getId());
		if(!goods2.getGoods().getSellerId().equals(sellerId)||!goods.getGoods().getSellerId().equals(sellerId)){
			return new Result(false,"非法操作");

		}
		try {
			goodsService.update(goods);
			return new Result(true, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "修改失败");
		}
	}	
	
	/**
	 * 获取实体
	 * @param id
	 * @return
	 */
	@RequestMapping("/findOne")
	public Goods findOne(Long id){
		return goodsService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public Result delete(Long [] ids){
		try {
			goodsService.delete(ids);
			return new Result(true, "删除成功"); 
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "删除失败");
		}
	}
	
		/**
	 * 查询+分页
	 *
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/search")
	public PageResult search(@RequestBody TbGoods goods, int page, int rows  ){
		//获取商家ID
		String sellerId=SecurityContextHolder.getContext().getAuthentication().getName();
		goods.setSellerId(sellerId);
		return goodsService.findPage(goods, page, rows);		
	}
@RequestMapping("/updateStatus")
	public Result updateStatus(Long[] ids,String status){
		try {
			goodsService.updateStatus(ids,status);
//			if("1".equals(status)){//如果是审核通过
//				//导入到索引库中
//				//得到需要导入到SKU列表
//				//List<TbItem> itemList=goodsService.findItemListByGoodsIdListAndStatus(id,status);
//				//导入到solr
//				//itemSearchService.importList(itemList);
//				//***生成商品详细页
//				for(Long goodsId:ids){
//					itemPageService.genItemHtml(goodsId);
//				}
//
//
//			}
			return new Result(true,"修改状态成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false,"修改状态失败");
		}

	}


	@Autowired
	private ItemPageService itemPageService;
	@RequestMapping("/genHtml")
	public void genHtml(Long goodsId){
		itemPageService.genItemHtml(goodsId);
	}
}
