import axios from 'axios';
import AuthService from './AuthService';

const LOCACAO_API_BASE_URL = 'http://localhost:8088/locacoes';

class LocacaoService {

    fetchLocacoes() {
        return axios.get(LOCACAO_API_BASE_URL + '?alugados=true', AuthService.getAuthHeader());
    }

    fetchHistorico() {
        return axios.get(LOCACAO_API_BASE_URL, AuthService.getAuthHeader());
    }

    fetchLocacaoById(locacaoId) {
        return axios.get(LOCACAO_API_BASE_URL + '/' + locacaoId, AuthService.getAuthHeader());
    }

    deleteLocacao(locacaoId) {
        return axios.delete(LOCACAO_API_BASE_URL + '/' + locacaoId, AuthService.getAuthHeader());
    }

    renovarLocacao(locacaoId) {
        return axios.put(LOCACAO_API_BASE_URL + '/renovar/' + locacaoId, AuthService.getAuthHeader());
    }

    addLocacao(locacao) {
        return axios.post(LOCACAO_API_BASE_URL, locacao, AuthService.getAuthHeader());
    }

    editLocacao(locacao) {
        return axios.put(LOCACAO_API_BASE_URL + '/' + locacao.id, locacao, AuthService.getAuthHeader());
    }

}

export default new LocacaoService();