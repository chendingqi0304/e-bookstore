import {Stomp} from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import {wsLink} from "./config";

let stompClient = null;
// 创建 STOMP 客户端
export const connectWebSocket = async (callback) => {
    // 使用 SockJS 创建 WebSocket 连接
    const account=sessionStorage.getItem('account');
    const socket = new SockJS(wsLink);
    stompClient =Stomp.over(socket)

    // 连接成功后的回调
    stompClient.onConnect = (frame) => {
        console.log('Connected: ' + frame);

        // 订阅 /topic/order 路径
        stompClient.subscribe('/user/'+JSON.parse(account).userId+'/order', function (message) {
            //console.log("Received message:", message.body);
            callback(message.body);
            stompClient.close();
            socket.close();
        });
    };

    // 连接错误时的处理
    stompClient.onStompError = (frame) => {
        console.error('Broker reported error: ' + frame.headers['message']);
        console.error('Additional details: ' + frame.body);
    };

    // 开始连接
    stompClient.activate();
};

// 发送消息
export const sendMessage = (orderMessage) => {
    if (stompClient && stompClient.connected) {
        stompClient.publish({
            destination: '/app/order', // 与服务器的 @MessageMapping("/order") 匹配
            body: orderMessage,
        });
    }
};
