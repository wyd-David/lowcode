import request from "@/utils/request";

//案例库单个同步
export function saveCaseIndexsPoolByBusinessKey(query) { 
    return request({
        url: "/archive/index/saveCaseIndexsPoolByBusinessKey",
        method: "get",
        params: query,
    });
}

//案例库全部同步
export function saveTimelyCaseIndexsPool(query) {
    return request({
        url: "/archive/index/saveTimelyCaseIndexsPool",
        method: "get",
        params: query,
    });
}

//过程库单个同步
export function saveProcessIndexsPoolByBusinessKey(query) {
    return request({
        url: "/archive/index/saveProcessIndexsPoolByBusinessKey",
        method: "get",
        params: query,
    });
}

//过程库全部同步
export function saveTimelyProcessIndexsPool(query) {
    return request({
        url: "/archive/index/saveTimelyProcessIndexsPool",
        method: "get",
        params: query,
    });
}

//制度库单个同步
export function saveRegulationIndexsPoolByBusinessKey(query) {
    return request({
        url: "/archive/index/saveRegulationIndexsPoolByBusinessKey",
        method: "get",
        params: query,
    });
}

//制度库全部同步
export function saveTimelyRegulationIndexsPool(query) {
    return request({
        url: "/archive/index/saveTimelyRegulationIndexsPool",
        method: "get",
        params: query,
    });
}