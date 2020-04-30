import axios from 'axios';
import AuthService from './AuthService';

const USUARIO_API_BASE_URL = 'http://localhost:8088/usuarios';

class UsuarioService {

    fetchUsuarios() {
        return axios.get(USUARIO_API_BASE_URL, AuthService.getAuthHeader());
    }

    fetchUsuarioById(usuarioId) {
        return axios.get(USUARIO_API_BASE_URL + '/' + usuarioId, AuthService.getAuthHeader());
    }

    deleteUsuario(usuarioCpf) {
        return axios.delete(USUARIO_API_BASE_URL + '/' + usuarioCpf, AuthService.getAuthHeader());
    }

    addUsuario(usuario) {
        return axios.post(USUARIO_API_BASE_URL + '/signup', usuario, AuthService.getAuthHeader());
    }

    editUsuario(usuario) {
        return axios.put(USUARIO_API_BASE_URL + '/' + usuario.id, usuario, AuthService.getAuthHeader());
    }

}

export default new UsuarioService();