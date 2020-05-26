import React, { Component } from 'react'
import UsuarioService from "../../service/UsuarioService";
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Button from '@material-ui/core/Button';
import CreateIcon from '@material-ui/icons/Create';
import DeleteIcon from '@material-ui/icons/Delete';
import Typography from '@material-ui/core/Typography';
import Container from '@material-ui/core/Container';
import NavBar from "../Navbar";
import AuthService from '../../service/AuthService';

class ListUsuarioComponent extends Component {

    constructor(props) {
        super(props)
        this.state = {
            usuarios: [],
            message: null
        }

        this.deleteUsuario = this.deleteUsuario.bind(this);
        this.editUsuario = this.editUsuario.bind(this);
        this.addUsuario = this.addUsuario.bind(this);
        this.reloadUsuarioList = this.reloadUsuarioList.bind(this);
    }

    componentDidMount() {
        if (!AuthService.getUserInfo())
            this.props.history.push('/login');
        this.reloadUsuarioList();
    }

    reloadUsuarioList() {
        UsuarioService.fetchUsuarios()
            .then((res) => {
                this.setState({ usuarios: res.data })
            }, err => {
                let statusCode = err.toString().split('status code ')[1].split(' ')[0]
                if (statusCode === '403' || statusCode === '500')
                    this.props.history.push('/login');
                else
                    this.setState({ message: 'Erro ao recarregar lista de usuários' });
            });
    }

    deleteUsuario(usuarioCpf) {
        UsuarioService.deleteUsuario(usuarioCpf)
            .then(res => {
                this.setState({ message: 'Usuario deletado com sucesso' });
                this.setState({ usuarios: this.state.usuarios.filter(usuario => usuario.cpf !== usuarioCpf) });
            }, err => {
                let statusCode = err.toString().split('status code ')[1].split(' ')[0]
                if (statusCode === '500')
                    this.props.history.push('/');
                else
                    this.setState({ message: 'Erro ao deletar usuário' });
            });
    }

    editUsuario(id) {
        window.localStorage.setItem("usuarioId", id);
        this.props.history.push('/edit-usuario');
    }

    addUsuario() {
        window.localStorage.removeItem("usuarioId");
        this.props.history.push('/add-usuario');
    }

    render() {
        return (
            <React.Fragment>
                <NavBar />
                <Container>
                    <Typography variant="h4" style={style}>Usuarios</Typography>
                    <Button variant="contained" color="primary" onClick={() => this.addUsuario()}>
                        Adicionar Usuário
                    </Button>

                    <Table>
                        <TableHead>
                            <TableRow>
                                <TableCell>Id</TableCell>
                                <TableCell align="right">CPF</TableCell>
                                <TableCell align="right">Nome</TableCell>
                                <TableCell align="right">Data Nascimento</TableCell>
                                <TableCell align="right">Sexo</TableCell>
                                <TableCell align="right">Roles</TableCell>
                                <TableCell align="right">Editar</TableCell>
                                <TableCell align="right">Excluir</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {this.state.usuarios.map(row => (
                                <TableRow key={row.id}>
                                    <TableCell component="th" scope="row">
                                        {row.id}
                                    </TableCell>
                                    <TableCell align="right">{row.cpf}</TableCell>
                                    <TableCell align="right">{row.nome}</TableCell>
                                    <TableCell align="right">{row.dataNascimento.split('-')[2]}/{row.dataNascimento.split('-')[1]}/{row.dataNascimento.split('-')[0]}</TableCell>
                                    <TableCell align="right">{row.sexo.tipo}</TableCell>
                                    <TableCell align="right">{row.roles[0]}</TableCell>
                                    {/* <TableCell align="right" onClick={() => alert("A API possui este recurso, mas ainda não foi implementado no front.")}><CreateIcon /></TableCell> */}
                                    {/* <TableCell align="right" onClick={() => this.deleteUsuario(row.cpf)}><DeleteIcon /></TableCell> */}
                                    <TableCell align="right"><Button variant="contained" color="primary" onClick={() => alert("A API possui este recurso, mas ainda não foi implementado no front.")}><CreateIcon /></Button></TableCell>
                                    <TableCell align="right"><Button variant="contained" color="primary" onClick={() => this.deleteUsuario(row.cpf)}><DeleteIcon /></Button></TableCell>

                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </Container>
                <Typography style={msg}>{this.state.message}</Typography>
            </React.Fragment>
        );
    }

}

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

export default ListUsuarioComponent;