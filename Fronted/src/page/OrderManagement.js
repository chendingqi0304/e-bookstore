import React, {useEffect, useState} from "react";
import {GetAllOrder, GetAllSelectedOrder, GetOrder, GetSelectedOrder} from "../utils/OrderAPI";
import {DatePicker, Table} from "antd";
import title from "../img/E-BookStore.png";
import Header from "../components/header";

const {RangePicker} = DatePicker;

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
];

const OrderManagement=()=>{
    const [orderList, setOrderList] = useState([]);
    const [startDate, setStartDate] = useState("");
    const [endDate, setEndDate] = useState("");

    useEffect(() => {
        const fetchOrder = async () => {
            const result = await GetAllOrder();
            console.log(result)
            if (result.code === 1) {
                for (let i = 0; i < result.data.length; i++) {
                    result.data[i].orderTime = result.data[i].orderTime.replace("T", " ")
                }
                setOrderList(result.data);
            } else {
                alert(result.msg)
            }
        }
        fetchOrder().then()
    }, []);

    const handleDateSelector = (dates, dateStrings) => {
        if (dates) {
            setStartDate(dateStrings[0]);
            setEndDate(dateStrings[1]);
        } else {
            console.log('Clear');
        }
    }

    const handleSearch = async (e) => {
        const formdata = new FormData();
        const title = document.getElementById("search").value;
        formdata.append("start", startDate);
        formdata.append("end", endDate);
        formdata.append("title", title);
        const result = await GetAllSelectedOrder(formdata);
        for (let i = 0; i < result.data.length; i++) {
            result.data[i].orderTime = result.data[i].orderTime.replace("T", " ")
        }
        console.log(result);
        setOrderList(result.data);
    }

    const showOrder = () => {
        if (orderList.length === 0) {
            return <></>
        } else {
            return <>
                {orderList.map((item, index) => <>
                    <div className="px-10 py-3 flex justify-between">
                        <div className="text-2xl">Order {index + 1}</div>
                        <div >
                            <div class="mx-3">用户userId：{item.userId}</div>
                            <div class="mx-3">下单时间：{item.orderTime}</div>
                        </div>

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
                                           <Table.Summary.Cell
                                               index={1}>￥{(total / 100).toFixed(2)}</Table.Summary.Cell>
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
        <div class="mx-20">
            <div className="text-3xl py-5">
                订单管理
            </div>
            <RangePicker onChange={handleDateSelector}></RangePicker>
            <div className="flex items-center">
                <input type="text" className="my-3 mx-3 border border-solid rounded-md" id="search"
                       placeholder={"搜索书名"}></input>
                <button onClick={handleSearch}
                        className="focus:outline-none text-sm w-auto py-3 rounded-md font-semibold text-white bg-blue-500 ring-4 flex p-4 h-6 justify-center items-center text-center gap-5">搜索
                </button>
            </div>
            {showOrder()}
        </div>

        </body>
        </html>
    </>;
}
export default OrderManagement;