import React, { Component, Fragment } from 'react'
import LocacaoService from "../../service/LocacaoService";
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import Container from '@material-ui/core/Container';
import NavBar from "../Navbar";
import InputMask from 'react-input-mask'
import AuthService from '../../service/AuthService';

class AddLocacaoComponent extends Component {

    constructor(props) {
        super(props);
        this.state = {
            dataDevolucao: '',
            usuarioId: '',
            filmeId: '',
            message: null
        }
        this.saveLocacao = this.saveLocacao.bind(this);
    }

    componentDidMount() {
        if (!AuthService.getUserInfo())
            this.props.history.push('/login');
    }

    saveLocacao = (e) => {
        e.preventDefault();
        let dataDevolucao = `${this.state.dataDevolucao.split('/')[2]}-${this.state.dataDevolucao.split('/')[1]}-${this.state.dataDevolucao.split('/')[0]}`
        let locacao = { usuarioId: this.state.usuarioId, filmeId: this.state.filmeId, dataDevolucao: dataDevolucao };
        LocacaoService.addLocacao(locacao)
            .then(res => {
                this.setState({ message: 'Filme alugado com sucesso!' });
                this.props.history.push('/lista-locacoes');
            }, err => {
                let statusCode = err.toString().split('status code ')[1].split(' ')[0]
                if (statusCode === '500')
                    this.props.history.push('/');
                else
                    this.setState({ message: 'Erro ao alugar filme' });
            });
    }

    onChange = (e) =>
        this.setState({ [e.target.name]: e.target.value });

    render() {
        return (
            <Fragment>
                <NavBar />
                <Container>
                    <Typography variant="h4" style={style}>Alugar Filme</Typography>
                    <form style={formContainer}>
                        <TextField label="ID DO USUÁRIO" type="number" fullWidth margin="normal" name="usuarioId" value={this.state.usuarioId} onChange={this.onChange} />

                        <TextField label="ID DO FILME" type="number" fullWidth margin="normal" name="filmeId" value={this.state.filmeId} onChange={this.onChange} />

                        {/* <TextField label="Data devolução" fullWidth margin="normal" name="dataDevolucao" value={this.state.dataDevolucao} onChange={this.onChange} /> */}
                        <InputMask
                            mask="99/99/9999"
                            value={this.state.dataDevolucao}
                            onChange={this.onChange}
                        >
                            {() => <TextField type="text" label="DATA DEVOLUÇÃO" fullWidth margin="normal" name="dataDevolucao" />}
                        </InputMask>
                        <Button variant="contained" color="primary" onClick={this.saveLocacao}>Salvar</Button>
                    </form>
                </Container>
                <Typography style={msg}>{this.state.message}</Typography>
            </Fragment>
        );
    }
}
const formContainer = {
    display: 'flex',
    flexFlow: 'row wrap'
};

const style = {
    display: 'flex',
    justifyContent: 'center',
    marginTop: 15
}

const msg = {
    display: 'flex',
    justifyContent: 'center',
    color: 'red',
    fontSize: 14,
    paddingTop: 15
}

export default AddLocacaoComponent;