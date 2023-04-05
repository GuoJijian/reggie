package com.guojijian.reggie.dto;


import com.guojijian.reggie.pojo.Setmeal;
import com.guojijian.reggie.pojo.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
