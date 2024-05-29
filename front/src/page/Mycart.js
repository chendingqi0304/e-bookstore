import title from "../img/E-BookStore.png";
import Header from "../components/header";
import React, {useEffect, useState} from "react";
import {Modal, Space, Table} from 'antd';
import {Checkbox, Col} from 'antd';
import {InputNumber} from 'antd';
import {useSelector} from "react-redux";

const Mycart = () => {
    const account = JSON.parse((useSelector((state) => state.account)).accountInfo);
    const [cartList, setCartList] = useState([]);
    const [selectedRowKeys, setSelectedRowKeys] = useState([]);

    const onSelectChange = (newSelectedRowKeys) => {
        //console.log('selectedRowKeys changed: ', newSelectedRowKeys);
        setSelectedRowKeys(newSelectedRowKeys);
    };
    const rowSelection = {
        selectedRowKeys,
        onChange: onSelectChange,
    };


    const handleCellChange = async (rowIndex, dataIndex, value) => {
        const newTableData = cartList;
        var cartId;
        const updateTable = newTableData.map((item) => {
            if (item.key === rowIndex) {
                //console.log(item.sum);
                cartId=item.cartId;
                return {
                    ...item,
                    number: value,
                    sum: value * newTableData[rowIndex]['price'],
                }
            }
            return item;
        });
        //console.log(updataTable);
        setCartList(updateTable);
        const formdata = new FormData;
        formdata.append("cartId", cartId);
        formdata.append("number", value);
        const header = new Headers();
        header.append("Content-Type", "multipart/form-data")
        const response = await fetch("http://localhost:8080/updateCartNum", {
            method: "POST",
            credentials: "include",
            //headers: header,
            body: formdata,
        })
        if (!response.ok) {
            alert("error");
        }
    }

    const columns = [
        {
            title: '商品',
            dataIndex: 'title',
            key: 'title',
            render: (text) => <a>{text}</a>,
        },
        {
            title: '单价',
            dataIndex: 'price',
            key: 'price',
            render: (price) => (
                <div>￥{(price/100).toFixed(2)}</div>
            )
        },
        {
            title: '数量',
            dataIndex: 'number',
            key: '',
            render: (number, record,rowIndex) => (
                <Space size="middle">
                    <InputNumber min={1} max={1000} defaultValue={number} onChange={(e) => handleCellChange(rowIndex, 'number', e)}/>
                </Space>
            ),
        },
        {
            title: '小计',
            key: 'sum',
            dataIndex: 'sum',
            render: (sum) => (
                <div>￥{(sum/100).toFixed(2)}</div>
            )
        },
        {
            title: '操作',
            key: 'action',
            render: (_, record) => (
                <Space size="middle">
                    <a>Delete</a>
                </Space>
            ),
        },
    ];

    const handleBuy=async () => {
        console.log(rowSelection.selectedRowKeys);
        const selectedKeys = rowSelection.selectedRowKeys;
        const formdata = new FormData();
        var selectedCartId=[];
        for (let i = 0; i < rowSelection.selectedRowKeys.length; i++) {



            cartList.map(async (item) => {
                if (item.key === selectedKeys[i]) {
                    selectedCartId.push(item.cartId)
                }
            })
        }
        formdata.append("cartIdList",selectedCartId)
        const response = await fetch("http://localhost:8080/BuybyCartIds", {
            method: "POST",
            body: formdata,
            credentials: "include"
        })
        window.location.href="/Main"
    }

    useEffect(() => {
        async function fetchCartList() {
            //console.log(account.userId);
            try {
                const response = await fetch("http://localhost:8080/getCart", {
                    method: "POST",
                    credentials: "include"
                })
                if (response.ok) {
                    const data = await response.json();
                    //console.log(data);
                    for(let i=0; i<data.length; i++) {
                        data[i].key=i;
                        data[i].sum=data[i].book.price*data[i].number;
                        data[i].price=data[i].book.price;
                        data[i].title=data[i].book.title;
                        console.log(i,data[i].book)

                    }
                    setCartList(data);
                } else {
                    alert("获取购物车列表失败");
                }
            } catch (error) {
                throw new Error("获取购物车列表失败", error)
            }
        }

        fetchCartList().then(r => {
        });
    }, []);

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
            我的购物车
        </div>
        <Table className="px-10" rowSelection={rowSelection} columns={columns} dataSource={cartList}/>
        <div className="flex items-center justify-center">
            <button onClick={handleBuy}
                className="focus:outline-none text-sm w-auto py-3 rounded-md font-semibold text-white bg-blue-500 ring-4 flex p-4 h-16 justify-center items-center text-center gap-5"
                type="submit">
                <svg className="icon text-white fill-current" viewBox="0 0 1024 1024"
                     width="30" height="30">
                    <path
                        d="M938.726888 481.287227c-0.722653-15.175723-13.007763-26.015526-28.183486-25.292873s-26.015526 13.730416-25.292873 28.183487c0 6.503881 0.722653 13.007763 0.722654 20.234297 0 211.737474-171.991531 384.451658-384.451659 384.451659s-384.451658-171.991531-384.451658-384.451659 171.991531-384.451658 384.451658-384.451658c56.366972 0 110.565984 11.562456 161.151729 35.410021l17.343684 8.671842c13.007763 7.226535 29.628793 2.16796 36.132675-11.562456 7.226535-13.007763 2.16796-29.628793-11.562456-36.132675-6.503881-3.613267-13.007763-6.503881-19.511644-9.394495-57.812279-26.738179-119.237826-40.468596-183.553988-40.468596-241.366267 0-437.928017 196.56175-437.928017 437.928017s196.56175 437.928017 437.928017 437.928017S939.449541 745.778405 939.449541 504.412138c0-7.949188-0.722653-15.175723-0.722653-23.124911z"
                    ></path>
                    <path
                        d="M893.199718 377.947777m-28.90614 0a28.90614 28.90614 0 1 0 57.812279 0 28.90614 28.90614 0 1 0-57.812279 0Z"
                    ></path>
                    <path
                        d="M849.840508 283.280169m-28.90614 0a28.90614 28.90614 0 1 0 57.81228 0 28.90614 28.90614 0 1 0-57.81228 0Z"
                    ></path>
                    <path
                        d="M779.743119 201.620325m-28.906139 0a28.90614 28.90614 0 1 0 57.812279 0 28.90614 28.90614 0 1 0-57.812279 0Z"
                    ></path>
                    <path
                        d="M622.927311 381.561044l36.855328-83.105151c5.781228-13.730416 0-29.628793-13.730416-35.410021-13.730416-5.781228-29.628793 0-35.410021 13.730416l-43.35921 98.280875-2.16796 6.503881h-99.726182c-0.722653-2.16796-0.722653-4.335921-2.167961-6.503881l-43.359209-98.280875c-5.781228-13.730416-21.679605-19.511644-35.410022-13.730416s-19.511644 21.679605-13.730416 35.410021l36.855328 83.105151h-96.112914c-15.175723 0-26.738179 12.285109-26.73818 26.73818s12.285109 26.738179 26.73818 26.738179h168.378264v137.304164h-168.378264c-15.175723 0-26.738179 12.285109-26.73818 26.738179s12.285109 26.738179 26.73818 26.738179h168.378264v134.41355c0 15.175723 12.285109 26.738179 26.738179 26.738179 15.175723 0 26.738179-12.285109 26.738179-26.738179v-134.41355h169.100917c15.175723 0 26.738179-12.285109 26.73818-26.738179s-12.285109-26.738179-26.73818-26.738179h-169.82357v-137.304164h169.100917c15.175723 0 26.738179-12.285109 26.738179-26.738179s-12.285109-26.738179-26.738179-26.73818h-78.769231z"
                    ></path>
                </svg>
                <span>立即购买</span>
            </button>
        </div>
        </body>
        </html>
    </>;
}
export default Mycart