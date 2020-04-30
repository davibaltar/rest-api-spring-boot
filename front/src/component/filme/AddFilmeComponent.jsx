import React, { Component, Fragment } from 'react'
import UsuarioService from "../../service/UsuarioService";
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import Container from '@material-ui/core/Container';
import NavBar from "../Navbar";

class AddUsuarioComponent extends Component {

    constructor(props) {
        super(props);
        this.state = {
            nome: '',
            quantidade: '',
            genero: '',
            diretor: '',
            message: null
        }
        this.saveUser = this.saveUser.bind(this);
    }

    saveUser = (e) => {
        e.preventDefault();
        let filme = { nome: this.state.nome, quantidade: this.state.quantidade, genero: { nome: this.state.genero }, diretor: { nome: this.state.diretor } };
        UsuarioService.addUser(filme)
            .then(res => {
                this.setState({ message: 'Filme adicionado com sucesso!' });
                this.props.history.push('/lista-filmes');
            }, err => {
                let statusCode = err.toString().split('status code ')[1].split(' ')[0]
                if (statusCode === '500')
                    this.props.history.push('/');
                else
                    this.setState({ message: 'Erro ao adicionar usuario' });
            });
    }

    onChange = (e) =>
        this.setState({ [e.target.name]: e.target.value });

    render() {
        return (
            <Fragment>
                <NavBar />
                <Container>
                    <Typography variant="h4" style={style}>Adicionar Filme</Typography>
                    <form style={formContainer}>

                        <TextField label="NOME" fullWidth margin="normal" name="nome" value={this.state.nome} onChange={this.onChange} />

                        <TextField label="QUANTIDADE" type="number" fullWidth margin="normal" name="quantidade" value={this.state.quantidade} onChange={this.onChange} />

                        <TextField label="GENERO" fullWidth margin="normal" name="genero" value={this.state.genero} onChange={this.onChange} />

                        <TextField label="DIRETOR" fullWidth margin="normal" name="diretor" value={this.state.diretor} onChange={this.onChange} />

                        <Button variant="contained" color="primary" onClick={this.saveUser}>Salvar</Button>
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

export default AddUsuarioComponent;