package com.buba.comtroller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.buba.pojo.ShoppingCart;
import com.buba.service.ShoppingCartService;
import com.buba.utils.BaseContext;
import com.buba.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/shoppingCart")
@Slf4j
public class ShoppingCartController {

    @Autowired
    ShoppingCartService shoppingCartService;

    //添加购物车
    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart) {
        log.info("购物车数据：{}", shoppingCart);
        //设置用户id，指定当前是哪个用户的购物车数据
        Long currentId = BaseContext.getCurrentId();
        shoppingCart.setUserId(currentId);

        //查询当前菜品或者套餐是否已经在购物车当中
        Long dishId = shoppingCart.getDishId();

        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, currentId);

        if (dishId != null) {
            //添加到购物车的为菜品
            queryWrapper.eq(ShoppingCart::getDishId, dishId);
        } else {
            //添加到购物车的为套餐
            queryWrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        }
        //SQL:select *from shopping_cart where user_id=? and dish_id/setmeal_id =?
        ShoppingCart cartServiceone = shoppingCartService.getOne(queryWrapper);

        if(cartServiceone!=null) {
            //如果已经存在，则在原来的基础上加一
            Integer number = cartServiceone.getNumber();
            cartServiceone.setNumber(number+1);
            shoppingCartService.updateById(cartServiceone);
        }else {
            //如果不存在，则添加到购物车中，默认为一
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartService.save(shoppingCart);
            cartServiceone=shoppingCart;
        }
        return R.success(cartServiceone);
    }

    //减少菜品
    @PostMapping("/sub")
    public R<ShoppingCart> sub(@RequestBody ShoppingCart shoppingCart){
        Long setmealId = shoppingCart.getSetmealId();
        Long dishId = shoppingCart.getDishId();
        LambdaQueryWrapper<ShoppingCart> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,BaseContext.getCurrentId());

        if (setmealId!=null){
            queryWrapper.eq(ShoppingCart::getSetmealId,setmealId);
        }else {
            queryWrapper.eq(ShoppingCart::getDishId,dishId);
        }
        ShoppingCart one = shoppingCartService.getOne(queryWrapper);
        Integer number = one.getNumber();
        if(number==1){
            shoppingCartService.remove(queryWrapper);
        }else {
            one.setNumber(number-1);
            shoppingCartService.updateById(one);
        }

        return R.success(one);
    }


    //查看购物车
    @GetMapping("/list")
    public R<List<ShoppingCart>> list(){
        log.info("查看购物车");
        LambdaQueryWrapper<ShoppingCart> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
        queryWrapper.orderByDesc(ShoppingCart::getCreateTime);
        List<ShoppingCart> list = shoppingCartService.list(queryWrapper);
        return R.success(list);
    }


    //清空购物车
    @DeleteMapping("/clean")
    public R<String> clean(){
        LambdaQueryWrapper<ShoppingCart> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,BaseContext.getCurrentId());
        shoppingCartService.remove(queryWrapper);
        return R.success("清空购物车成功");
    }
}
