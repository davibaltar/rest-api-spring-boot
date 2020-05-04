import React from 'react';

const AlertBox = (props) => {

    const errorMsg = (msg) => {
        return (
            <div style={{ ...styles.alert, ...styles.error }}>
                <button type="button" style={styles.closeAlert} onClick={props.close}>×</button>
                {msg}
            </div>
        )
    }

    const infoMsg = (msg) => {
        return (
            <div style={{ ...styles.alert, ...styles.info }}>
                <button type="button" style={styles.closeAlert} onClick={props.close}>×</button>
                {msg}
            </div>
        )
    }

    const warningMsg = (msg) => {
        return (
            <div style={{ ...styles.alert, ...styles.warning }}>
                <button type="button" style={styles.closeAlert} onClick={props.close}>×</button>
                {msg}
            </div>
        )
    }

    const successMsg = (msg) => {
        return (
            <div style={{ ...styles.alert, ...styles.success }}>
                <button type="button" style={styles.closeAlert} onClick={props.close}>×</button>
                {msg}
            </div>
        )
    }

    return (
        <React.Fragment>
            {props.message && (!props.type || props.type.toLowerCase() === 'error') ? errorMsg(props.message) : ''}
            {props.message && props.type.toLowerCase() === 'info' ? infoMsg(props.message) : ''}
            {props.message && props.type.toLowerCase() === 'warning' ? warningMsg(props.message) : ''}
            {props.message && props.type.toLowerCase() === 'success' ? successMsg(props.message) : ''}
        </React.Fragment>
    )
}

const styles = {
    alert: {
        minWidth: 150,
        padding: 15,
        marginBottom: 20,
        border: '1px solid transparent',
        borderRadius: 3
    },
    error: {
        backgroundColor: '#ECC8C5',
        borderColor: '#AB8889',
        color: '#B1312E',
        marginTop: 40
    },
    info: {
        backgroundColor: '#CDE9F6',
        borderColor: '#96B5C8',
        color: '#437EAF',
        marginTop: 40
    },
    warning: {
        backgroundColor: '#F7F4D6',
        borderColor: '#D1CDA8',
        color: '#937330',
        marginTop: 40
    },
    success: {
        backgroundColor: '#DDF3D6',
        borderColor: '#B6C6B0',
        color: '#5A7051',
        marginTop: 40
    },
    closeAlert: {
        position: 'relative',
        float: 'right',
        padding: 0,
        border: 0,
        cursor: 'pointer',
        color: 'inherit',
        background: '0 0',
        fontSize: 21,
        lineHeight: 1,
        fontWeight: 'bold',
        textShadow: '0 1px 0 rgba(255,255,255,.7)',
        filter: 'alpha(opacity=40)',
        opacity: '.4'
    }
}


export default AlertBox;