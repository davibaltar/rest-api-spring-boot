import axios from 'axios';

const USER_API_BASE_URL = 'http://localhost:8088/usuarios/';

class AuthService {

    login(credentials) {
        return new Promise((resolve, reject) => {
            try {
                axios.post(USER_API_BASE_URL + "signin", credentials).then(ret => {
                    return resolve(ret)
                }, err => {
                    return resolve(err)
                });

                // return axios.post(USER_API_BASE_URL + "signin", credentials)

            } catch (err) {
                return reject({ status: 500 })
            }
        })
    }

    validade() {
        return new Promise((resolve, reject) => {
            try {
                axios.post(USER_API_BASE_URL + "perfil").then(ret => {
                    return resolve(ret)
                }, err => {
                    return resolve(err)
                });
            } catch (err) {
                return reject({ status: 500 })
            }
        })
    }

    getUserInfo() {
        return JSON.parse(localStorage.getItem("userInfo"));
    }

    getAuthHeader() {
        try {
            return { headers: { Authorization: 'Bearer ' + this.getUserInfo().token } };
        } catch (err) {
            return false
        }
    }

    logOut() {
        // localStorage.clear();
        // let test = JSON.parse(localStorage.getItem("userInfo"));
        // return axios.post(USER_API_BASE_URL + 'logout', {}, this.getAuthHeader());
        localStorage.removeItem("userInfo");
        return true;

    }
}

export default new AuthService();