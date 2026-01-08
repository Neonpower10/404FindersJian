function apiBase() {
    const ctx = window.location.pathname.split("/")[1];
    return `${window.location.origin}/${ctx}/api`;
}

function authHeaders() {
    const token = localStorage.getItem("token");
    return token ? { Authorization: `Bearer ${token}` } : {};
}
