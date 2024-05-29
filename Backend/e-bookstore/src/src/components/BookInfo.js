
export default function BookInfo(bookInfo) {
    return (
        <>
            <div className="h-auto w-2/3 p-5 leading-9 rounded-xl ring-2 ring-black ring-opacity-5">
                <div className="text-3xl text-red-700">{bookInfo.name}</div>
                <div className="text-gray-700">作者：{bookInfo.author}</div>
                <div className="text-gray-700">类别：{bookInfo.type}</div>
                <div className="text-gray-700">定价：<span className="text-red-700 text-2xl">￥163.70</span></div>
                <div className="text-gray-700">状态：{bookInfo.state}
                    <span className="text-gray-400 pl-2">库存 {bookInfo.rest} 本</span>
                </div>
                <div className="text-gray-700">简介：{bookInfo.Intro}</div>
            </div>
        </>);
}