export const TOKEN_KEY = 'token'
import instance from '@/libs/api/index'

export const setToken = (token) => {
    sessionStorage.setItem(TOKEN_KEY, token)
}

export const getToken = () => {
    const token = sessionStorage.getItem(TOKEN_KEY)
    if (token) {
        instance.defaults.headers.common['Authorization'] = 'Bearer ' + token;
        return token
    } else
        return false
}

/**
 * 把list转换成tree
 * @param list
 * @returns {*}
 */
export const listToTree = (list) => {
    let map = {};
    list.forEach(item => {
        if (! map[item.id]) {
            map[item.id] = item;
        }
    });

    list.forEach(item => {
        if (item.parentId) {
            map[item.parentId].children ? map[item.parentId].children.push(item) : map[item.parentId].children = [item];
        }
    });

    return list.filter(item => {
        if (!item.parentId) {
            return item;
        }
    })
}
