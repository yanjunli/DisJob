package com.hqyg;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.curator.framework.CuratorFramework;
import org.junit.Test;

import com.hqyg.disjob.common.util.LocalHost;
import com.hqyg.disjob.register.cache.ZKJobCache;
import com.hqyg.disjob.register.center.RpcZKRegistry;
import com.hqyg.disjob.register.repository.ZnodeApiCuratorImpl;
import com.hqyg.disjob.register.rpc.ZookeeperRegistry;
import com.hqyg.disjob.register.utils.ZooKeeperRegistryUtils;
import com.hqyg.disjob.rpc.client.HURL;

public class RpcZKRegistryTest extends BaseJunitTest{
    @Resource
    private RpcZKRegistry rpcZKRegistry;
    
     @Test
     public void initClientTest() throws Exception{
         CuratorFramework client = rpcZKRegistry.getClient();
         System.out.println(client.getState());
         ZnodeApiCuratorImpl znode = new ZnodeApiCuratorImpl();
        
         znode.createEphemeral(client, "/ejob/rpc/oms/test/providers/192.168.99.ccc:15", "ejob://10.40.6.74:74/test1?phpFilePath=/usr/local/rpc-project/test.php&className=Test&methodName=start&version=0.1");
         List<String> groupList =  ZKJobCache.groupList;
         Map<String, List<String>> serverMap = ZKJobCache.serverMap;
        printNodes(groupList, serverMap, client);
        System.out.println("===================");
        znode.createEphemeral(client, "/ejob/rpc/oms/test/providers/192.168.99.ddd:99", "ejob://10.40.6.75:7/test1?phpFilePath=/usr/local/rpc-project/test.php&className=Test&methodName=start&version=0.1");
         groupList =  ZKJobCache.groupList;
          serverMap = ZKJobCache.serverMap;
         printNodes(groupList, serverMap, client);
         Thread.sleep(Integer.MAX_VALUE);
     }
     
     private void printNodes(List<String> groupList , Map<String, List<String>> serverMap,
                CuratorFramework client) throws Exception
      {
        /*  List<String> currentChilds = client.getChildren().forPath(parentNode);
          if(CollectionUtils.isNotEmpty(currentChilds)){
              for(String str : currentChilds){
                  String groupNode = parentNode+"/"+str;
                  if(!groupList.contains(groupNode)){
                       groupList.add(groupNode);
                  }
                  
                 List<String> serverNames =  client.getChildren().forPath(groupNode);
                 if(CollectionUtils.isNotEmpty(serverNames)){
                     
                     serverMap.put(groupNode, serverNames);
                 }
              }
          }*/
          for(String ls : groupList){
              System.out.println("group node :"+ls);
          }
        /* Iterator<String> items = serverMap.keySet().iterator();
          while(items.hasNext()){
              String key = items.next();
              System.out.println(key+","+serverMap.get(key));
          }*/
          for(Map.Entry<String, List<String>> entry : serverMap.entrySet()){
              System.out.println("group node :"+ entry.getKey());
              for(String str :entry.getValue()){
                  System.out.println("server node :"+  str);
                  HURL url = new HURL(str.split("/")[3], new LocalHost().getIp(), str.split("/")[4]);
                  printHURL(url);
              }
          }
          
          
      }
     
     
     public void printHURL(HURL hurl){
         List<HURL> map =  ZookeeperRegistry.subscribedCategoryResponses.get(hurl);
          if(map !=null){
               
                  for(HURL h : map){
                      System.out.println("rpc url is "+h.toAllString());
                  }
              
          }
         
     }
     public static void main(String[] args)
    {
        String str ="/ejob/rpc/oms/test";
        System.out.println(str.split("/")[2]);  
        System.out.println(str.split("/")[3]);

    }

}
