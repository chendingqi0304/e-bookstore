import {createSlice} from "@reduxjs/toolkit";


const storedAccount = sessionStorage.getItem('account');

const accountInfo =storedAccount? storedAccount:null;//空账号为0

const initialState = {
    accountInfo:accountInfo
}

const accountSlice = createSlice(

    {
        name: 'account',
        initialState,
        reducers: {
            changeAccount: (state, accountData) => {
                sessionStorage.setItem("account", JSON.stringify(accountData.payload));
                state.accountInfo=JSON.stringify(accountData.payload);
            }
        }
    }
);
export const {changeAccount} = accountSlice.actions;
export default accountSlice.reducer;