import React, { Component } from 'react'
import FilmeService from "../../service/FilmeService";
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
            filmes: [],
            message: null
        }

        this.deleteUser = this.deleteUser.bind(this);
        this.editUser = this.editUser.bind(this);
        this.addUser = this.addUser.bind(this);
        this.reloadUserList = this.reloadUserList.bind(this);
    }

    componentDidMount() {
        if (!AuthService.getUserInfo())
            this.props.history.push('/login');
        this.reloadUserList();
    }

    reloadUserList() {
        FilmeService.fetchUsers()
            .then((res) => {
                this.setState({ filmes: res.data })
            }, err => {
                let statusCode = err.toString().split('status code ')[1].split(' ')[0]
                if (statusCode === '500')
                    this.props.history.push('/');
                else
                    this.setState({ message: 'Erro ao recarregar lista de filmes' });
            });
    }

    deleteUser(userId) {
        FilmeService.deleteUser(userId)
            .then(res => {
                this.setState({ message: 'User deleted successfully.' });
                this.setState({ filmes: this.state.filmes.filter(user => user.id !== userId) });
            }, err => {
                let statusCode = err.toString().split('status code ')[1].split(' ')[0]
                if (statusCode === '500')
                    this.props.history.push('/');
                else
                    this.setState({ message: 'Erro ao remover filme' });
            });
    }

    editUser(id) {
        window.localStorage.setItem("userId", id);
        this.props.history.push('/edit-user');
    }

    addUser() {
        window.localStorage.removeItem("userId");
        this.props.history.push('/add-user');
    }

    render() {
        return (
            <React.Fragment>
                <NavBar />
                <Container>
                    <Typography variant="h4" style={style}>Filmes</Typography>
                    <Button variant="contained" color="primary" onClick={() => alert("A API possui este recurso, mas ainda não foi implementado no front.")}>
                        Adicionar Filme
                    </Button>

                    <Table>
                        <TableHead>
                            <TableRow>
                                <TableCell>Id</TableCell>
                                <TableCell align="right">Filme</TableCell>
                                <TableCell align="right">Qntd. Estoque</TableCell>
                                <TableCell align="right">Genero</TableCell>
                                <TableCell align="right">Diretor</TableCell>
                                <TableCell align="right">Editar</TableCell>
                                <TableCell align="right">Excluir</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {this.state.filmes.map(row => (
                                <TableRow key={row.id}>
                                    <TableCell component="th" scope="row">
                                        {row.id}
                                    </TableCell>
                                    <TableCell align="right">{row.nome}</TableCell>
                                    <TableCell align="right">{row.quantidade}</TableCell>
                                    <TableCell align="right">{row.genero.nome}</TableCell>
                                    <TableCell align="right">{row.diretor.nome}</TableCell>
                                    {/* <TableCell align="right" onClick={() => alert("A API possui este recurso, mas ainda não foi implementado no front.")}><CreateIcon /></TableCell> */}
                                    {/* <TableCell align="right" onClick={() => this.deleteUser(row.id)}><DeleteIcon /></TableCell> */}
                                    <TableCell align="right"><Button variant="contained" color="primary" onClick={() => alert("A API possui este recurso, mas ainda não foi implementado no front.")}><CreateIcon /></Button></TableCell>
                                    <TableCell align="right"><Button variant="contained" color="primary" onClick={() => this.deleteUser(row.id)}><DeleteIcon /></Button></TableCell>

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