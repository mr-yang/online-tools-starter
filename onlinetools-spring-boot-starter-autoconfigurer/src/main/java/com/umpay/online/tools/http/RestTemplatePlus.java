package com.umpay.online.tools.http;

import com.alibaba.fastjson.JSON;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.umpay.online.tools.base.HttpResult;
import com.umpay.online.tools.config.OnlineToolsConfig;
import com.umpay.online.tools.enums.RetMsgEnum;
import com.umpay.online.tools.util.LoggerTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: xiaoyang
 * @Date: 2020/1/9 16:12
 * @Description:封装RestTemplate会把异常信息返回 https://blog.csdn.net/w1014074794/article/details/105889199/
 */
@Component
public class RestTemplatePlus {

    @Resource
    private RestTemplate restTemplate;

    @Autowired
    private OnlineToolsConfig onlineToolsConfig;

    /**
     * post请求json格式
     *
     * @param url
     * @param request
     * @param responseType
     * @param uriVariables
     * @param <T>
     * @return
     */
    public <T> HttpResult<T> postForObject(String url,
                                           @Nullable Object request,
                                           Class<T> responseType,
                                           Object... uriVariables) {
        return doPostForObject(url, request, MediaType.APPLICATION_JSON_UTF8, responseType, null, uriVariables);
    }


    /**
     * post请求json格式
     *
     * @param url
     * @param request
     * @param responseType
     * @param uriVariables
     * @param <T>
     * @return
     */
    public <T> HttpResult<T> postForObject(String url,
                                           @Nullable Object request,
                                           MediaType mediaType,
                                           Class<T> responseType,
                                           Object... uriVariables) {
        return doPostForObject(url, request, mediaType, responseType, null, uriVariables);
    }

    /**
     * post请求json格式
     *
     * @param url
     * @param request
     * @param responseType
     * @param uriVariables
     * @param <T>
     * @return
     */
    public <T> HttpResult<T> postForObject(String url,
                                           @Nullable Object request,
                                           Class<T> responseType,
                                           Map<String, ?> uriVariables) {
        return doPostForObject(url, request, MediaType.APPLICATION_JSON_UTF8, responseType, uriVariables);
    }

    /**
     * post请求json格式
     *
     * @param url
     * @param request
     * @param responseType
     * @param uriVariables
     * @param <T>
     * @return
     */
    public <T> HttpResult<T> postForObject(String url,
                                           @Nullable Object request,
                                           MediaType mediaType,
                                           Class<T> responseType,
                                           Map<String, ?> uriVariables) {
        return doPostForObject(url, request, mediaType, responseType, uriVariables);
    }


    /**
     * post请求xml格式
     *
     * @param url
     * @param request
     * @param responseType
     * @param uriVariables
     * @param <T>
     * @return
     */
    public <T> HttpResult<T> postForObjectXML(String url,
                                              @Nullable Object request,
                                              Class<T> responseType,
                                              Object... uriVariables) {
        return doPostForObject(url, request, MediaType.APPLICATION_XML, responseType, null, uriVariables);

    }


    /**
     * post请求xml格式
     *
     * @param url
     * @param request
     * @param responseType
     * @param uriVariables
     * @param <T>
     * @return
     */
    public <T> HttpResult<T> postForObjectXML(String url,
                                              @Nullable Object request,
                                              MediaType mediaType,
                                              Class<T> responseType,
                                              Object... uriVariables) {
        return doPostForObject(url, request, mediaType, responseType, null, uriVariables);

    }

    /**
     * post请求xml格式
     *
     * @param url
     * @param request
     * @param responseType
     * @param uriVariables
     * @param <T>
     * @return
     */
    public <T> HttpResult<T> postForObjectXML(String url,
                                              @Nullable Object request,
                                              Class<T> responseType,
                                              Map<String, ?> uriVariables) {
        return doPostForObject(url, request, MediaType.APPLICATION_XML, responseType, uriVariables);
    }


    /**
     * post请求xml格式
     *
     * @param url
     * @param request
     * @param responseType
     * @param uriVariables
     * @param <T>
     * @return
     */
    public <T> HttpResult<T> postForObjectXML(String url,
                                              @Nullable Object request,
                                              MediaType mediaType,
                                              Class<T> responseType,
                                              Map<String, ?> uriVariables) {
        return doPostForObject(url, request, mediaType, responseType, uriVariables);
    }

    /**
     * post最终方法，支持json和xml，url变量替换
     *
     * @param url             请求地址
     * @param request         请求参数
     * @param mediaType       请求参数类型
     * @param responseType    响应对象类型
     * @param uriMapVariables url可变参数
     * @param uriVariables    url可变参数
     * @param <T>
     * @return
     */
    private  <T> HttpResult<T> doPostForObject(String url, @Nullable Object request, MediaType mediaType, Class<T> responseType, Map<String, ?> uriMapVariables, Object... uriVariables) {

        try {
            LoggerTools.info("post请求地址：" + url);
            LoggerTools.info("请求报文", request);
            String requestStr;
            if (MediaType.APPLICATION_XML == mediaType) {
                requestStr = new XStream().toXML(request);
            } else {
                requestStr = JSON.toJSONString(request);
            }
            LoggerTools.info("mediaType数据", mediaType.toString());
            HttpEntity<String> requestEntity = new HttpEntity<>(requestStr, getHttpHeaders(mediaType));
            T data;
            if (uriVariables != null && uriVariables.length > 0) {
                LoggerTools.info("url变量数据", uriVariables);
                data = restTemplate.postForObject(url, requestEntity, responseType, uriVariables);
                LoggerTools.info("响应报文", data);
                return HttpResult.ok(data);
            }
            if (uriMapVariables != null) {
                LoggerTools.info("url变量数据", uriMapVariables);
                data = restTemplate.postForObject(url, requestEntity, responseType, uriMapVariables);
                LoggerTools.info("响应报文", data);
                return HttpResult.ok(data);
            }
            data = restTemplate.postForObject(url, requestEntity, responseType);
            LoggerTools.info("响应报文", data);
            return HttpResult.ok(data);
        } catch (HttpClientErrorException.NotFound e) {
            LoggerTools.error("请求没有找到404", e);
            return HttpResult.create(RetMsgEnum.NOT_FOUND);
        } catch (ResourceAccessException e) {
            return handleException(e);
        } catch (Exception e) {
            LoggerTools.error("未知请求异常", e);
            return HttpResult.create(RetMsgEnum.FAIL);
        }

    }


    //get分为2种，1种是restful风格的，1种是普通key=value风格的/////////////////////////////////////////


    /**
     * get请求，restful风格的，
     * 请求地址是：http://127.0.0.1:8081/getTestResponseBean/{name}/{age}
     *
     * @param url          请求地址
     * @param responseType 响应对象类型
     * @param uriVariables 可变参数，按顺序替换name，age
     * @param <T>
     * @return
     */
    public <T> HttpResult<T> getForObject(String url,
                                          Class<T> responseType,
                                          Object... uriVariables) {
        return doGetForObject(url, MediaType.APPLICATION_JSON_UTF8,responseType,  null, uriVariables);
    }


    /**
     * get请求，restful风格的，
     * 请求地址是：http://127.0.0.1:8081/getTestResponseBean/{name}/{age}
     *
     * @param url          请求地址
     * @param responseType 响应对象类型
     * @param uriVariables 可变参数，按顺序替换name，age
     * @param <T>
     * @return
     */
    public <T> HttpResult<T> getForObject(String url,
                                          MediaType mediaType,
                                          Class<T> responseType,
                                          Object... uriVariables) {
        return doGetForObject(url, mediaType,responseType,  null, uriVariables);
    }

    /**
     * get请求，restful风格的，
     * 请求地址是：http://127.0.0.1:8081/getTestResponseBean/{name}/{age}
     *
     * @param url             请求地址
     * @param responseType    响应对象类型
     * @param uriMapVariables url可变参数，map的key需要和name，age相同
     * @param <T>
     * @return
     */
    public <T> HttpResult<T> getForObject(String url,
                                          Class<T> responseType,
                                          Map<String, ?> uriMapVariables) {
        return doGetForObject(url, MediaType.APPLICATION_JSON_UTF8,responseType,  uriMapVariables);
    }

    /**
     * get请求，restful风格的，
     * 请求地址是：http://127.0.0.1:8081/getTestResponseBean/{name}/{age}
     *
     * @param url             请求地址
     * @param responseType    响应对象类型
     * @param uriMapVariables url可变参数，map的key需要和name，age相同
     * @param <T>
     * @return
     */
    public <T> HttpResult<T> getForObject(String url,
                                          MediaType mediaType,
                                          Class<T> responseType,
                                          Map<String, ?> uriMapVariables) {
        return doGetForObject(url, mediaType,responseType,  uriMapVariables);
    }


    /**
     * get请求，普通key=value风格的，
     * 请求地址是：http://127.0.0.1:8081/getTestResponseBean?name=hehe&age=18
     *
     * @param url             请求地址
     * @param responseType    响应对象类型
     * @param uriMapVariables url可变参数，map的key需要和name，age相同
     * @param <T>
     * @return
     */
    public <T> HttpResult<T> getStandardForObject(String url,
                                                  Class<T> responseType,
                                                  Map<String, ?> uriMapVariables) {
        if (uriMapVariables != null && uriMapVariables.entrySet().size() != 0) {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
            uriMapVariables.entrySet().stream().forEach(param -> builder.queryParam(param.getKey(), param.getValue()));
            url = builder.build().encode().toString();
        }
        return doGetForObject(url, MediaType.APPLICATION_JSON_UTF8,responseType,  null);
    }

    /**
     * get请求，普通key=value风格的，
     * 请求地址是：http://127.0.0.1:8081/getTestResponseBean?name=hehe&age=18
     *
     * @param url             请求地址
     * @param responseType    响应对象类型
     * @param uriMapVariables url可变参数，map的key需要和name，age相同
     * @param <T>
     * @return
     */
    public <T> HttpResult<T> getStandardForObject(String url,
                                                  Class<T> responseType,
                                                  MediaType mediaType,
                                                  Map<String, ?> uriMapVariables) {
        if (uriMapVariables != null && uriMapVariables.entrySet().size() != 0) {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
            uriMapVariables.entrySet().stream().forEach(param -> builder.queryParam(param.getKey(), param.getValue()));
            url = builder.build().encode().toString();
        }
        return doGetForObject(url, mediaType,responseType,  null);
    }


    private HttpHeaders getHttpHeaders(MediaType mediaType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);
        ArrayList<MediaType> acceptableMediaTypes = new ArrayList<>();
        acceptableMediaTypes.add(mediaType);
        headers.setAccept(acceptableMediaTypes);
        return headers;
    }

    /**
     * get请求
     *
     * @param url             请求地址
     * @param responseType    响应对象类型
     * @param uriMapVariables url可变参数
     * @param uriVariables    url可变参数
     * @param <T>
     * @return
     */
    private <T> HttpResult<T> doGetForObject(String url, MediaType mediaType, Class<T> responseType, Map<String, ?> uriMapVariables, Object... uriVariables) {
        try {
            LoggerTools.info("get请求地址：" + url);
            HttpEntity<String> requestEntity = new HttpEntity<>(null, getHttpHeaders(mediaType));
            RequestCallback requestCallback = restTemplate.httpEntityCallback(requestEntity, responseType);
            HttpMessageConverterExtractor<T> responseExtractor =
                    new HttpMessageConverterExtractor<>(responseType, restTemplate.getMessageConverters());
            T data;
            if (uriVariables != null && uriVariables.length > 0) {
                LoggerTools.info("url变量数据", uriVariables);
                data = restTemplate.execute(url, HttpMethod.GET, requestCallback, responseExtractor, uriVariables);
                LoggerTools.info("响应报文", data);
                return HttpResult.ok(data);
            }
            if (uriMapVariables != null) {
                LoggerTools.info("url变量数据", uriMapVariables);
                data = restTemplate.execute(url, HttpMethod.GET, requestCallback, responseExtractor, uriMapVariables);
                LoggerTools.info("响应报文", data);
                return HttpResult.ok(data);
            }
            data = restTemplate.execute(url, HttpMethod.GET, requestCallback, responseExtractor);
            LoggerTools.info("响应报文", data);
            return HttpResult.ok(data);
        } catch (HttpClientErrorException.NotFound e) {
            LoggerTools.error("请求没有找到404", e);
            return HttpResult.create(RetMsgEnum.NOT_FOUND);
        } catch (ResourceAccessException e) {
            return handleException(e);
        } catch (Exception e) {
            LoggerTools.error("未知请求异常", e);
            return HttpResult.create(RetMsgEnum.FAIL);
        }
    }
    /**
     * @author : gaozhiguo
     * @date : 2020-03-25 14:22
     * @version : V1.0
     * @description : form表单形式,默认编码bgk
     **/
    public <T> HttpResult<T> postForFormParams(String url, Object requestParam, Class<T> responseType) {
        return doPostForFormParams(url,getHttpHeaders(MediaType.parseMediaType("application/x-www-form-urlencoded;charset=GBK")),requestParam,responseType);
    }
    /**
     * @author : tianxiaoyang
     * @date : 2020-03-25 14:22
     * @version : V1.0
     * @description : form表单形式,默认编码UTF8
     **/
    public <T> HttpResult<T> postForFormParamsUTF8(String url, Object requestParam, Class<T> responseType) {
        return doPostForFormParams(url,getHttpHeaders(MediaType.parseMediaType("application/x-www-form-urlencoded;charset=UTF-8")),requestParam,responseType);
    }

    /**
     * @author : tianxiaoyang
     * @date : 2020-03-25 14:22
     * @version : V1.0
     * @description : form表单形式,可以传入HttpHeaders
     **/
    public <T> HttpResult<T> postForFormParams(String url,HttpHeaders headers,Object requestParam, Class<T> responseType) {
        return doPostForFormParams(url,headers,requestParam,responseType);
    }


    /**
     * @author : tianxiaoyang
     * @date : 2020-03-25 14:22
     * @version : V1.0
     * @description : form表单形式
     **/
    public <T> HttpResult<T> doPostForFormParams(String url,HttpHeaders headers, Object requestParam, Class<T> responseType) {
        try {
            LoggerTools.info("请求路径：" + url);
            LoggerTools.info("请求报文：", requestParam);
            MultiValueMap<String, String> requestParamMap = new LinkedMultiValueMap();
            HashMap<String, String> map = JSON.parseObject(JSON.toJSONString(requestParam), HashMap.class);
            for (Map.Entry<String, String> entry : map.entrySet()) {
                requestParamMap.add(entry.getKey(), entry.getValue());
            }
            HttpEntity<MultiValueMap<String, String>> requestData = new HttpEntity<>(requestParamMap, headers);
            T data = restTemplate.postForObject(url, requestData, responseType);
            LoggerTools.info("响应报文：", data);
            return HttpResult.ok(data);
        } catch (HttpClientErrorException.NotFound e) {
            LoggerTools.error("请求没有找到404", e);
            return HttpResult.create(RetMsgEnum.NOT_FOUND);
        } catch (ResourceAccessException e) {
            return handleException(e);
        } catch (Exception e) {
            LoggerTools.error("未知请求异常", e);
            return HttpResult.create(RetMsgEnum.FAIL);
        }

    }
    /**
     * @author : tianxiaoyang
     * @date : 2020-03-25 14:22
     * @version : V1.0
     * @description : form表单形式
     **/
    public <T> HttpResult<T> doPostForFormParams1(String url, Object requestParam, MediaType mediaType, Class<T> responseType) {
        try {
            LoggerTools.info("请求路径：" + url);
            LoggerTools.info("请求报文：", requestParam);
            MultiValueMap<String, String> requestParamMap = new LinkedMultiValueMap();
            HashMap<String, String> map = JSON.parseObject(JSON.toJSONString(requestParam), HashMap.class);
            for (Map.Entry<String, String> entry : map.entrySet()) {
                requestParamMap.add(entry.getKey(), entry.getValue());
            }
            HttpEntity<MultiValueMap<String, String>> requestData = new HttpEntity<>(requestParamMap, getHttpHeaders(mediaType));
            T data = restTemplate.postForObject(url, requestData, responseType);
            LoggerTools.info("响应报文：", data);
            return HttpResult.ok(data);
        } catch (HttpClientErrorException.NotFound e) {
            LoggerTools.error("请求没有找到404", e);
            return HttpResult.create(RetMsgEnum.NOT_FOUND);
        } catch (ResourceAccessException e) {
            return handleException(e);
        } catch (Exception e) {
            LoggerTools.error("未知请求异常", e);
            return HttpResult.create(RetMsgEnum.FAIL);
        }

    }


    /**
     * @author : gaozhiguo
     * @date : 2020-04-20 14:21
     * @version : V1.0
     * @description :发送特殊xml-map请求
     **/
    public <T> HttpResult<T> sendRequestForXml(String method, String url, Object request, Class<T> responseType) {
        HttpURLConnection conn = null;
        InputStreamReader reader = null;
        XStream xstream = new XStream(new DomDriver());
        xstream.allowTypes(new Class[]{responseType});
        Object obj = null;
        LoggerTools.info("请求路径：" + url);
        try {
            URL urlPath = new URL(url);
            conn = (HttpURLConnection) urlPath.openConnection();
            conn.setConnectTimeout(onlineToolsConfig.getOkhttp().getConnectTimeout()*1000);
            conn.setReadTimeout(onlineToolsConfig.getOkhttp().getReadTimeout()*1000);
            conn.setRequestMethod(method);
            if (request != null) {
                conn.setDoOutput(true);
                conn.getOutputStream().write(xstream.toXML(JSON.parseObject(JSON.toJSONString(request), Map.class)).getBytes(StandardCharsets.UTF_8));
                conn.getOutputStream().flush();
                conn.getOutputStream().close();
            }
            int rCode = conn.getResponseCode();

            if (rCode == HttpURLConnection.HTTP_OK) {
                if (conn.getInputStream() != null) {
                    reader = new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8);
                    obj = xstream.fromXML(reader);
                }
                LoggerTools.info("请求成功：" + obj.toString());
                return HttpResult.ok(JSON.parseObject(JSON.toJSONString(obj), responseType));
            }
        } catch (Exception e) {
            LoggerTools.info("请求异常", e);
        } finally {
            try {
                reader.close();
                conn.disconnect();
            } catch (Exception e) {
            }
        }
        LoggerTools.info("请求失败");
        return HttpResult.create(RetMsgEnum.FAIL);
    }

    private <T> HttpResult<T> handleException(ResourceAccessException e) {
        //连接超时和读超时都会返回这个异常
        //connect timed out 连接超时，timeout读超时
        Throwable cause = e.getCause();
        if (cause != null) {
            if (cause instanceof SocketTimeoutException) {
                if ("connect timed out".equals(cause.getMessage())) {
                    LoggerTools.error("连接超时异常", e);
                    return HttpResult.create(RetMsgEnum.CONNECT_TIMED_OUT);
                }
                if ("timeout".equals(cause.getMessage())) {
                    LoggerTools.error("读超时异常", e);
                    return HttpResult.create(RetMsgEnum.TIME_OUT);
                }
            }
        }
        LoggerTools.error("ResourceAccessException异常", e);
        return HttpResult.create(RetMsgEnum.FAIL);
    }
}
