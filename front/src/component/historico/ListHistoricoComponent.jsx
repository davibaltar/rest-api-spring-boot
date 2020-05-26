import React, { Component } from 'react'
import LocacaoService from "../../service/LocacaoService";
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableRow from '@material-ui/core/TableRow';
import Typography from '@material-ui/core/Typography';
import Container from '@material-ui/core/Container';
import NavBar from "../Navbar";
import AuthService from '../../service/AuthService';

class ListHistoricoComponent extends Component {

    constructor(props) {
        super(props)
        this.state = {
            historicos: [],
            message: null
        }

        this.reloadLocacaoList = this.reloadLocacaoList.bind(this);
    }

    componentDidMount() {
        if (!AuthService.getUserInfo())
            this.props.history.push('/login');
        this.reloadLocacaoList();
    }

    reloadLocacaoList() {
        LocacaoService.fetchHistorico()
            .then((res) => {
                this.setState({ historicos: res.data })
            }, err => {
                let statusCode = err.toString().split('status code ')[1].split(' ')[0]
                if (statusCode === '500')
                    this.props.history.push('/');
                else
                    this.setState({ message: 'Erro ao recarregar histórico' });
            });
    }

    render() {
        return (
            <React.Fragment>
                <NavBar />
                <Container>
                    <Typography variant="h4" style={style}>Histórico de Locação</Typography>
                    <Table>
                        <TableBody>
                            {this.state.historicos.map(row => (
                                <TableRow key={row.id}>
                                    <TableCell component="th" scope="row">
                                        O filme "{row.filme.nome}" foi alugado por "{row.usuario.nome}" no dia {row.createdAt.split(' ')[0].split('-')[2]}/{row.createdAt.split(' ')[0].split('-')[1]}/{row.createdAt.split(' ')[0].split('-')[0]}.
                                    </TableCell>
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

export default ListHistoricoComponent;