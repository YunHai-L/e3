package cn.e3mall.controller;

import cn.e3mall.common.utils.FastDFSClient;
import cn.e3mall.common.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: YunHai
 * @Date: 2018/11/26 14:10
 * @Description:
 */
@Controller
public class PictureController {

    @Value("${IMAGE_SERVER_URL}")
    private String IMAGE_SERVER_URL;


//    参数名和富文本编辑器的filePostName一致
    @RequestMapping(value="/pic/upload",produces= MediaType.TEXT_PLAIN_VALUE+";charset=utf-8")
    @ResponseBody
    public String uploadFile(MultipartFile uploadFile){
        Map result = new HashMap();
        try{
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:conf/client.conf");
//            获取文件路径  和文件的后缀(扩展名: 获取全名.获取全名的最后一个.的位置+1  截取后面的数据)
            String url = fastDFSClient.uploadFile(uploadFile.getBytes(),uploadFile.getOriginalFilename().substring(uploadFile.getOriginalFilename().lastIndexOf(".")+1));
            url = IMAGE_SERVER_URL + url;

//           设置信息
            result.put("url",url);
            result.put("error",0);
        }catch (Exception e){
            e.printStackTrace();
//            设置错误数据
            result.put("error",1);
            result.put("message",e.getMessage());
        }


        return JsonUtils.objectToJson(result);
//        return result;
    }
}
