import { configureStore } from "@reduxjs/toolkit";
import accountStore from "./modules/accountStore";
import backendLinkStore from "./modules/backendLinkStore";

const store = configureStore({
    reducer: {
        account: accountStore,
        backendLink:backendLinkStore,
    },
});

export default store;