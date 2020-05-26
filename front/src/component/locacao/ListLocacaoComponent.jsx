import React, { Component } from 'react'
import LocacaoService from "../../service/LocacaoService";
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Button from '@material-ui/core/Button';
import AddIcon from '@material-ui/icons/Add';
import Typography from '@material-ui/core/Typography';
import Container from '@material-ui/core/Container';
import NavBar from "../Navbar";
import AuthService from '../../service/AuthService';

class ListLocacaoComponent extends Component {

    constructor(props) {
        super(props)
        this.state = {
            locacoes: [],
            message: null
        }

        this.deleteLocacao = this.deleteLocacao.bind(this);
        this.editLocacao = this.editLocacao.bind(this);
        this.renovarLocacao = this.renovarLocacao.bind(this);
        this.addLocacao = this.addLocacao.bind(this);
        this.reloadLocacaoList = this.reloadLocacaoList.bind(this);
    }

    componentDidMount() {
        if (!AuthService.getUserInfo())
            this.props.history.push('/login');
        this.reloadLocacaoList();
    }

    reloadLocacaoList() {
        LocacaoService.fetchLocacoes()
            .then((res) => {
                this.setState({ locacoes: res.data })
            }, err => {
                let statusCode = err.toString().split('status code ')[1].split(' ')[0]
                if (statusCode === '500')
                    this.props.history.push('/');
                else
                    this.setState({ message: 'Erro ao recarregar lista de locações' });
            });
    }

    deleteLocacao(locacaoId) {
        LocacaoService.deleteLocacao(locacaoId)
            .then(res => {
                this.setState({ message: 'Locacao deletada com sucesso.' });
                this.setState({ locacoes: this.state.locacoes.filter(locacao => locacao.id !== locacaoId) });
            }, err => {
                let statusCode = err.toString().split('status code ')[1].split(' ')[0]
                if (statusCode === '500')
                    this.props.history.push('/');
                else
                    this.setState({ message: 'Erro ao deletar locação' });
            });
    }

    renovarLocacao(locacaoId) {
        LocacaoService.renovarLocacao(locacaoId)
            .then(res => {
                this.setState({ message: 'Locacao renovada com sucesso.' });
                this.setState({
                    locacoes: this.state.locacoes.map(locacao => {
                        if (locacao.id === locacaoId)
                            locacao.renovacoes += 1
                        return locacao
                    })
                });

                this.props.history.push('/lista-locacoes');
            }, err => {
                let statusCode = err.toString().split('status code ')[1].split(' ')[0]
                if (statusCode === '500')
                    this.props.history.push('/');
                else
                    this.setState({ message: 'Não é permitido mais de 2 renovações.' });
            });
    }

    editLocacao(id) {
        window.localStorage.setItem("locacaoId", id);
        this.props.history.push('/edit-locacao');
    }

    addLocacao() {
        window.localStorage.removeItem("locacaoId");
        this.props.history.push('/add-locacao');
    }

    render() {
        return (
            <React.Fragment>
                <NavBar />
                <Container>
                    <Typography variant="h4" style={style}>Filmes Alugados</Typography>
                    <Button variant="contained" color="primary" onClick={() => this.addLocacao()}>
                        Alugar Filme
                    </Button>

                    <Table>
                        <TableHead>
                            <TableRow>
                                <TableCell>Id</TableCell>
                                <TableCell align="right">Filme</TableCell>
                                <TableCell align="right">Usuario</TableCell>
                                <TableCell align="right">Data Devolução</TableCell>
                                <TableCell align="right">Renovações</TableCell>
                                <TableCell align="right">Renovar</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>

                            {this.state.locacoes.map(row => (
                                <TableRow key={row.id}>
                                    <TableCell component="th" scope="row">
                                        {row.id}
                                    </TableCell>
                                    <TableCell align="right">{row.filme.nome}</TableCell>
                                    <TableCell align="right">{row.usuario.nome}</TableCell>
                                    <TableCell align="right">{row.dataDevolucao.split('-')[2]}/{row.dataDevolucao.split('-')[1]}/{row.dataDevolucao.split('-')[0]}</TableCell>
                                    <TableCell align="right">{row.renovacoes}</TableCell>
                                    {/* <TableCell align="right" onClick={() => this.renovarLocacao(row.id)}><CreateIcon /></TableCell> */}
                                    <TableCell align="right"><Button variant="contained" color="primary" onClick={() => this.renovarLocacao(row.id)}><AddIcon /></Button></TableCell>
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


export default ListLocacaoComponent;