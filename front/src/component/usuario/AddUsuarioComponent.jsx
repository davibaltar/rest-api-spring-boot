import React, { Component, Fragment } from 'react'
import UsuarioService from "../../service/UsuarioService";
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import Container from '@material-ui/core/Container';
import NavBar from "../Navbar";
import InputMask from 'react-input-mask'
import Radio from '@material-ui/core/Radio';
import RadioGroup from '@material-ui/core/RadioGroup';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import FormControl from '@material-ui/core/FormControl';
import FormLabel from '@material-ui/core/FormLabel';
import AuthService from '../../service/AuthService';

class AddUsuarioComponent extends Component {

    constructor(props) {
        super(props);
        this.state = {
            cpf: '',
            nome: '',
            senha: '',
            dataNascimento: '',
            sexo: 'm',
            roles: 'ROLE_EMPLOYEE',
            message: null
        }
        this.saveUsuario = this.saveUsuario.bind(this);
    }

    componentDidMount() {
        if (!AuthService.getUserInfo())
            this.props.history.push('/login');
    }

    saveUsuario = (e) => {
        e.preventDefault();
        let dataNascimentoFormat = `${this.state.dataNascimento.split('/')[2]}-${this.state.dataNascimento.split('/')[1]}-${this.state.dataNascimento.split('/')[0]}`
        let usuario = { cpf: this.state.cpf.split('.').join('').split('-').join(''), nome: this.state.nome, senha: this.state.senha, dataNascimento: dataNascimentoFormat, sexo: { tipo: this.state.sexo }, roles: [this.state.roles] };
        UsuarioService.addUsuario(usuario)
            .then(res => {
                this.setState({ message: 'Usuario adicionado com sucesso!' });
                this.props.history.push('/lista-usuarios');
            }, err => {
                let statusCode = err.toString().split('status code ')[1].split(' ')[0]
                if (statusCode === '500')
                    this.props.history.push('/');
                else
                    this.setState({ message: 'Erro ao adicionar usuário' });
            });
    }

    onChange = (e) =>
        this.setState({ [e.target.name]: e.target.value });


    render() {

        return (
            <Fragment>
                <NavBar />
                <Container>
                    <Typography variant="h4" style={style}>Adicionar Usuário</Typography>
                    <form style={formContainer}>

                        <InputMask
                            mask="999.999.999-99"
                            value={this.state.cpf}
                            onChange={this.onChange}
                        >
                            {() => <TextField type="text" label="CPF" fullWidth margin="normal" name="cpf" />}
                        </InputMask>

                        <TextField label="SENHA" type="password" fullWidth margin="normal" name="senha" value={this.state.senha} onChange={this.onChange} />

                        <TextField label="NOME" fullWidth margin="normal" name="nome" value={this.state.nome} onChange={this.onChange} />

                        <InputMask
                            mask="99/99/9999"
                            value={this.state.dataNascimento}
                            onChange={this.onChange}
                        >
                            {() => <TextField type="text" label="DATA NASCIMENTO" fullWidth margin="normal" name="dataNascimento" />}
                        </InputMask>
                        {/* <TextField label="SEXO" fullWidth margin="normal" name="sexo" value={this.state.sexo} onChange={this.onChange} /> */}
                        <Container>
                            <FormControl style={{ marginTop: 25 }} component="fieldset">
                                <FormLabel component="legend">Sexo</FormLabel>
                                <RadioGroup aria-label="position" name="sexo" value={this.state.sexo} onChange={this.onChange} row>
                                    <FormControlLabel value="m" control={<Radio color="primary" />} label="Masculino" labelPlacement="end" />
                                    <FormControlLabel value="f" control={<Radio color="primary" />} label="Feminino" labelPlacement="end" />
                                </RadioGroup>
                            </FormControl>
                        </Container>
                        {/* <TextField label="ROLES" placeholder="ROLE_ADMIN, ROLE_EMPLOYEE, ROLE_CLIENT" fullWidth margin="normal" name="roles" value={this.state.roles} onChange={this.onChange} /> */}
                        <Container>
                            <FormControl style={{ marginTop: 25 }} component="fieldset">
                                <FormLabel component="legend">Permissão</FormLabel>
                                <RadioGroup aria-label="position" name="roles" value={this.state.roles} onChange={this.onChange} row>
                                    <FormControlLabel value="ROLE_ADMIN" control={<Radio color="primary" />} label="Administrador" labelPlacement="end" />
                                    <FormControlLabel value="ROLE_EMPLOYEE" control={<Radio color="primary" />} label="Funcionario" labelPlacement="end" />
                                    <FormControlLabel value="ROLE_CLIENT" control={<Radio color="primary" />} label="Cliente" labelPlacement="end" />
                                </RadioGroup>
                            </FormControl>
                        </Container>

                        <Button style={{ marginTop: 25 }} variant="contained" color="primary" onClick={this.saveUsuario}>Salvar</Button>
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