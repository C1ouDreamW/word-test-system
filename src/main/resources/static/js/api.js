/**
 * 统一的 API 请求封装工具
 * 自动处理 fetch 的繁琐配置，自动解析后端的 ApiResult 统一返回体
 */
const API = {
    // 基础请求方法
    request: async (url, options = {}) => {
        const defaultHeaders = {
            'Content-Type': 'application/json'
        };
        // 合并配置
        const config = {
            ...options,
            headers: { ...defaultHeaders, ...options.headers }
        };

        try {
            const response = await fetch(url, config);
            const result = await response.json();
            
            // 按照你们后端的统一规范：200才是成功
            if (result.code === 200) {
                return result.data; // 成功的话，直接把业务数据剥离出来返回，页面用起来更清爽
            } else {
                // 失败直接弹窗提示后端的错误信息
                alert(result.message || '请求失败');
                return Promise.reject(result.message);
            }
        } catch (error) {
            console.error('网络请求异常:', error);
            alert('网络请求异常，请检查后端是否正常启动！');
            return Promise.reject(error);
        }
    },

    // GET 查询
    get: (url) => API.request(url, { method: 'GET' }),
    
    // POST 新增
    post: (url, data) => API.request(url, { method: 'POST', body: JSON.stringify(data) }),
    
    // PUT 修改
    put: (url, data) => API.request(url, { method: 'PUT', body: JSON.stringify(data) }),
    
    // DELETE 删除
    delete: (url) => API.request(url, { method: 'DELETE' })
};