import React, {useEffect, useState} from "react";
import {GetAllOrder, GetAllSelectedOrder, GetOrder, GetSelectedOrder} from "../utils/OrderAPI";
import {DatePicker, Pagination, Table} from "antd";
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

const OrderManagement = () => {
    const [orderList, setOrderList] = useState([]);
    const [startDate, setStartDate] = useState("");
    const [endDate, setEndDate] = useState("");

    const [currentPage, setCurrentPage] = useState(1);
    const [curLength, setLength] = useState(0)

    const handlePageChange = (page) => {
        setCurrentPage(page);
    };


    useEffect(() => {
        const fetchOrder = async () => {
            const formData = new FormData();
            formData.append("index", 0);
            formData.append("size", 10);
            const result = await GetAllOrder(formData);
            console.log(result)
            if (result.code === 1) {
                for (let i = 0; i < result.data.content.length; i++) {
                    result.data.content[i].orderTime = result.data.content[i].orderTime.replace("T", " ")
                }
                setOrderList(result.data.content);
                setLength(result.data.totalElements - 1 || 0);
            } else {
                alert(result.msg)
            }
        }
        fetchOrder().then()
    }, []);

    useEffect(() => {
        getSearchlist(currentPage-1).then()
    }, [currentPage]);

    const handleDateSelector = (dates, dateStrings) => {
        if (dates) {
            setStartDate(dateStrings[0]);
            setEndDate(dateStrings[1]);
        } else {
            console.log('Clear');
        }
    }
const getSearchlist=async(index)=>{
    const formdata = new FormData();
    const title = document.getElementById("search").value;
    formdata.append("start", startDate);
    formdata.append("end", endDate);
    formdata.append("title", title);
    formdata.append("index",index);
    formdata.append("size", 10);
    const result = await GetAllSelectedOrder(formdata);
    console.log(result)
    for (let i = 0; i < result.data.content.length; i++) {
        result.data.content[i].orderTime = result.data.content[i].orderTime.replace("T", " ")
    }
    console.log(result);
    setOrderList(result.data.content);
    setLength(result.data.totalElements - 1 || 0);
}
    const handleSearch = async (e) => {
        setCurrentPage(1);
        await getSearchlist(0);
    }

    const showOrder = () => {
        if (orderList.length === 0) {
            return <></>
        } else {
            return <>
                {orderList.map((item, index) => <>
                    <div className="px-10 py-3 flex justify-between">
                        <div className="text-2xl">Order {(currentPage - 1) * 10 + index + 1}</div>
                        <div>
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
                <div className="flex justify-center mt-4">
                    <Pagination
                        current={currentPage}
                        total={curLength}
                        onChange={handlePageChange}
                    />
                </div>

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