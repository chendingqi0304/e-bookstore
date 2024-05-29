import title from "../img/E-BookStore.png";
import Header from "../components/header";
import React, {useEffect, useState} from "react";

import {Table} from 'antd';
import {useSelector} from "react-redux";
import TextArea from "antd/es/input/TextArea";

const columns = [
    {
        title: '商品',
        dataIndex: 'name',
        key: 'name',
        width: "50%",
    },
    {
        title: '数量',
        dataIndex: 'number',
        key: 'number',
        width: "20%",
    },
    {
        title: '付款金额',
        key: 'price',
        dataIndex: 'price',
        width: "30%",
        render: (price) => (
            <div>￥{(price / 100).toFixed(2)}</div>
        )
    },
    // {
    //     title: '下单时间',
    //     key: 'orderTime',
    //     dataIndex: 'orderTime',
    // }
];

const Myorder = () => {
    const account = JSON.parse((useSelector((state) => state.account)).accountInfo);
    const [orderList, setOrderList] = useState([]);

    useEffect(() => {
        async function fetchOrderList() {
            //console.log(account.userId);
            try {
                const response = await fetch("http://localhost:8080/getOrder", {
                    method: "POST",
                    credentials: "include"
                })
                if (response.ok) {
                    const data = await response.json();
                    console.log(data);
                    for (let i = 0; i < data.length; i++) {
                        data[i].orderTime = data[i].orderTime.replace("T", " ")
                    }
                    setOrderList(data);

                } else {
                    alert("获取订单列表失败");
                }
            } catch (error) {
                throw new Error("获取订单列表失败", error)
            }
        }

        fetchOrderList().then(r => {
        });
    }, []);

    const showOrder = () => {
        if (orderList.length === 0) {
            return <></>
        } else {
            return <>
                {orderList.map((item, index) => <>
                    <div className="px-10 py-3 flex justify-between">
                        <div className="text-2xl">Order {index + 1}</div>
                        <div>下单时间：{item.orderTime}</div>
                    </div>

                    <Table className="px-10" columns={columns} dataSource={item.orderItems} pagination={false}
                           summary={(data) => {
                               let total = 0;
                               data.forEach(({price}) => {
                                   total += price
                               })
                               return (
                                   <>
                                       <Table.Summary.Row>
                                           <Table.Summary.Cell index={0}>总计</Table.Summary.Cell>
                                           <Table.Summary.Cell index={1}></Table.Summary.Cell>
                                           <Table.Summary.Cell index={1}>￥{(total/100).toFixed(2)}</Table.Summary.Cell>
                                       </Table.Summary.Row>
                                   </>
                               )
                           }}/>
                </>)}
            </>
        }

    }
    return <>
        <html lang="en">
        <head>
            <meta charset="UTF-8"/>
            <title>E-BookStore</title>
            <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
            <link href="../dist/output.css" rel="stylesheet"/>
        </head>
        <body>
        <Header></Header>
        <div className="text-3xl pl-20 py-5">
            我的订单
        </div>
        {showOrder()}

        <div className="flex items-center justify-center">

        </div>
        </body>
        </html>
    </>;
}
export default Myorder