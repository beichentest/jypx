/*
 * Copyright (c) 2017 <l_iupeiyu@qq.com> All rights reserved.
 */

package com.geekcattle.util.console;

import com.geekcattle.model.cms.UserDistrict;
import com.geekcattle.model.console.Menu;
import com.geekcattle.model.console.Role;
import com.geekcattle.model.console.RoleMenu;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * author geekcattle
 * date 2016/10/21 0021 下午 15:58
 */
public class DistrictTreeUtil {

    private List<UserDistrict> nodes;
    
    private List<UserDistrict> rootNodes;
   
    /**
     * 创建一个新的实例 Tree.
     *
     * @param nodes   将树的所有节点都初始化进来。
     */
    public DistrictTreeUtil(List<UserDistrict> nodes, List<UserDistrict> rootNodes){
        this.nodes = nodes;
        this.rootNodes = rootNodes;
    }


    /**
     * buildTree
     * 描述:  创建树
     * @return List<Map<String,Object>>
     * @exception
     * @since  1.0.0
     */
    public List<Map<String, Object>> buildTree(){
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        for(UserDistrict node : rootNodes) {            
        	Map<String, Object> map = buildTreeChildsMap(node);
            list.add(map);            
        }
        return list;
    }


    /**
     * buildChilds
     * 描述:  创建树下的节点。
     * @param node
     * @return List<Map<String,Object>>
     * @exception
     * @since  1.0.0
     */
    private List<Map<String, Object>> buildTreeChilds(UserDistrict node){
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        List<UserDistrict> childNodes = getChilds(node);
        for(UserDistrict childNode : childNodes){
            Map<String, Object> map = buildTreeChildsMap(childNode);
            list.add(map);
        }
        return list;
    }

    /**
     * buildChildMap
     * 描述:生成Map节点
     * @param childNode
     * @return Map<String, Object>
     */
    private Map<String, Object> buildTreeChildsMap(UserDistrict childNode){
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> stateMap = new HashMap<>();
        stateMap.put("checked", false);
        /*for(RoleMenu checknode : checknodes){
            if(checknode.getMenuId().equals(childNode.getMenuId())){
                stateMap.put("checked", true);
            }
        }*/
        stateMap.put("disabled", false);
        stateMap.put("expanded", false);
        stateMap.put("selected", false);
        map.put("id", childNode.getDistrictId());
        map.put("text", childNode.getDistrictName());
        //map.put("url", childNode.get);
        map.put("state", stateMap);
        List<Map<String, Object>> childs = buildTreeChilds(childNode);
        if(childs.isEmpty() || childs.size() == 0){
            //map.put("state","open");
        }else{
            map.put("nodes", childs);
        }
        return map;
    }


    /**
     * getChilds
     * 描述:  获取子节点
     * @param parentNode
     * @return List<Resource>
     * @exception
     * @since  1.0.0
     */
    public List<UserDistrict> getChilds(UserDistrict parentNode) {
        List<UserDistrict> childNodes = new ArrayList<UserDistrict>();
        for(UserDistrict node : nodes){
            //System.out.println(node.getParentId()+"-------"+parentNode.getId());
            if (StringUtils.equals(node.getParentId(), parentNode.getDistrictId())) {
                childNodes.add(node);
            }
        }
        return childNodes;
    }

    /**
     * buildTree
     * 描述:  创建树
     * @return List<Map<String,Object>>
     * @exception
     * @since  1.0.0
     */
    public List<UserDistrict> buildTreeGrid(){
        List<UserDistrict> list = new ArrayList<UserDistrict>();
        for(UserDistrict node : rootNodes) {
            //这里判断父节点，需要自己更改判断           
            List<UserDistrict> childs = buildTreeGridChilds(node);
            node.setChildren(childs);
            list.add(node);
        }
        return list;
    }

    /**
     * buildChilds
     * 描述:  创建树下的节点。
     * @param node
     * @return List<Map<String,Object>>
     * @exception
     * @since  1.0.0
     */
    private List<UserDistrict> buildTreeGridChilds(UserDistrict node){
        List<UserDistrict> list = new ArrayList<UserDistrict>();
        List<UserDistrict> childNodes = getChilds(node);
        for(UserDistrict childNode : childNodes){
            List<UserDistrict> childs = buildTreeGridChilds(childNode);
            childNode.setChildren(childs);
            list.add(childNode);
        }
        return list;
    }




}
