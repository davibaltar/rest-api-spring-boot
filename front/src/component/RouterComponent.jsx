import { BrowserRouter as Router, Route, Switch } from 'react-router-dom'

import ListFilmeComponent from "./filme/ListFilmeComponent";
import AddFilmeComponent from "./filme/AddFilmeComponent";
import EditFilmeComponent from "./filme/EditFilmeComponent";

import ListLocacaoComponent from "./locacao/ListLocacaoComponent";
import AddLocacaoComponent from "./locacao/AddLocacaoComponent";
import EditLocacaoComponent from "./locacao/EditLocacaoComponent";

import ListUsuarioComponent from "./usuario/ListUsuarioComponent";
import AddUsuarioComponent from "./usuario/AddUsuarioComponent";
import EditUserComponent from "./usuario/EditUserComponent";

import ListHistoricoComponent from "./historico/ListHistoricoComponent";

import React from "react";
import LoginComponent from "./usuario/LoginComponent";

const AppRouter = () => {
    return (
        <Router>
            <Switch>
                <Route path="/" exact component={LoginComponent} />

                <Route path="/lista-filmes" component={ListFilmeComponent} />
                <Route path="/add-filme" component={AddFilmeComponent} />
                <Route path="/editar-filme" component={EditFilmeComponent} />

                <Route path="/lista-locacoes" component={ListLocacaoComponent} />
                <Route path="/add-locacao" component={AddLocacaoComponent} />
                <Route path="/editar-locacao" component={EditLocacaoComponent} />

                <Route path="/lista-usuarios" component={ListUsuarioComponent} />
                <Route path="/add-usuario" component={AddUsuarioComponent} />
                <Route path="/editar-usuario" component={EditUserComponent} />

                <Route path="/lista-historico" component={ListHistoricoComponent} />

            </Switch>
        </Router>
    )
}

export default AppRouter;