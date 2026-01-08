document.addEventListener('DOMContentLoaded', () => {
    if (!document.getElementById('campingName')) return;

    // Storage keys
    const STORAGE_KEY = 'campingData';
    const HISTORY_KEY = 'campingHistory';

    // DOM references
    const campingName = document.getElementById('campingName');
    const street = document.getElementById('street');
    const city = document.getElementById('city');
    const phone = document.getElementById('phone');
    const email = document.getElementById('email');
    const openingHours = document.getElementById('openingHours');
    const price = document.getElementById('price');

    const saveBtn = document.getElementById('saveBtn');
    const resetBtn = document.getElementById('resetBtn');
    const restoreLatestBtn = document.getElementById('restoreLatestBtn');
    const openSiteBtn = document.getElementById('openSiteBtn');

    const toast = document.getElementById('toast');
    const historyList = document.getElementById('historyList');

    // Preview refs
    const pvName = document.getElementById('pv-name');
    const pvAddress = document.getElementById('pv-address');
    const pvContact = document.getElementById('pv-contact');
    const pvHours = document.getElementById('pv-hours');
    const pvPrice = document.getElementById('pv-price');

    /**
     * Returns default camping data when no data is available.
     * @returns {Object} Default camping details.
     */
    function defaultData() {
        return {
            name: 'Rust & Natuur Camping',
            street: 'Voorbeeldstraat 1',
            city: '1234 AB, Dorp',
            phone: '+31 6 0000 0000',
            email: 'info@camping.example',
            openingHours: 'Dagelijks 09:00 - 21:00',
            pricePerNight: 20.00,
            updatedAt: new Date().toISOString()
        };
    }

    /**
     * Loads camping data from localStorage or returns default data.
     * @returns {Object} Camping data.
     */
    function loadData() {
        try {
            const raw = localStorage.getItem(STORAGE_KEY);
            return raw ? JSON.parse(raw) : defaultData();
        } catch {
            return defaultData();
        }
    }

    /**
     * Saves camping data to localStorage and updates history.
     * @param {Object} data - Camping data to save.
     * @returns {boolean} True if save was successful, false otherwise.
     */
    function saveData(data) {
        try {
            localStorage.setItem(STORAGE_KEY, JSON.stringify(data));
            addHistory(data);
            return true;
        } catch {
            return false;
        }
    }

    /**
     * Adds a snapshot of data to history and trims if exceeding limit.
     * @param {Object} data - Camping data snapshot.
     */
    function addHistory(data) {
        let arr = JSON.parse(localStorage.getItem(HISTORY_KEY) || '[]');
        arr.unshift({snapshot: data, at: new Date().toISOString()});
        if (arr.length > 20) arr.pop();
        localStorage.setItem(HISTORY_KEY, JSON.stringify(arr));
        renderHistory();
    }

    /**
     * Retrieves all history snapshots from localStorage.
     * @returns {Array} Array of history objects.
     */
    function getHistory() {
        return JSON.parse(localStorage.getItem(HISTORY_KEY) || '[]');
    }

    /**
     * Fills the form inputs with given camping data.
     * @param {Object} data - Camping data to display in the form.
     */
    function applyToForm(data) {
        campingName.value = data.name || '';
        street.value = data.street || '';
        city.value = data.city || '';
        phone.value = data.phone || '';
        email.value = data.email || '';
        openingHours.value = data.openingHours || '';
        price.value = data.pricePerNight || '';
    }

    /**
     * Updates preview section with camping data.
     * @param {Object} data - Camping data to display in preview.
     */
    function applyToPreview(data) {
        pvName.innerText = data.name;
        pvAddress.innerText = `${data.street} · ${data.city}`;
        pvContact.innerText = `${data.phone} · ${data.email}`;
        pvHours.innerText = data.openingHours;
        pvPrice.innerText = Number(data.pricePerNight).toFixed(2);
    }

    /**
     * Reads data from the form inputs and constructs an object.
     * @returns {Object} Camping data from the form.
     */
    function readForm() {
        return {
            name: campingName.value.trim(),
            street: street.value.trim(),
            city: city.value.trim(),
            phone: phone.value.trim(),
            email: email.value.trim(),
            openingHours: openingHours.value.trim(),
            pricePerNight: Number(price.value) || 0,
            updatedAt: new Date().toISOString()
        };
    }

    /**
     * Renders the list of saved history snapshots in the UI.
     */
    function renderHistory() {
        const arr = getHistory();
        historyList.innerHTML = '';
        if (arr.length === 0) {
            historyList.innerText = 'Nog geen versies beschikbaar.';
            return;
        }
        arr.forEach((h) => {
            const div = document.createElement('div');
            div.className = 'history-item';
            const info = document.createElement('div');
            info.innerText = `${h.snapshot.name} (${new Date(h.at).toLocaleString()})`;
            const btn = document.createElement('button');
            btn.innerText = 'Herstel';
            btn.className = 'btn-ghost';
            btn.onclick = () => {
                if (confirm('Versie herstellen?')) {
                    saveData(h.snapshot);
                    applyToForm(h.snapshot);
                    applyToPreview(h.snapshot);
                }
            };
            div.appendChild(info);
            div.appendChild(btn);
            historyList.appendChild(div);
        });
    }

    /**
     * Displays a temporary notification (toast) on the screen.
     * @param {string} msg - Message to display.
     * @param {string} [type='info'] - Type of toast ('info', 'success', 'error').
     */
    function showToast(msg, type = 'info') {
        toast.innerText = msg;
        toast.style.background = type === 'success' ? 'var(--success)' :
            type === 'error' ? 'var(--danger)' : '#0ea5a0';
        toast.className = 'toast show';
        setTimeout(() => toast.className = 'toast', 2500);
    }

    // Events
    saveBtn.onclick = () => {
        const data = readForm();
        if (!data.name || !data.email) {
            showToast('Vul verplichte velden in', 'error');
            return;
        }
        if (saveData(data)) {
            applyToPreview(data);
            showToast('Opgeslagen!', 'success');
        }
    };
    resetBtn.onclick = () => applyToForm(loadData());
    restoreLatestBtn.onclick = () => {
        const h = getHistory();
        if (h[0]) {
            applyToForm(h[0].snapshot);
            applyToPreview(h[0].snapshot);
            showToast('Laatste versie hersteld', 'success');
        }
    };
    openSiteBtn.onclick = () => alert('Simulatie van publieke site (hier kun je later een echte pagina openen).');

    // Init
    const data = loadData();
    applyToForm(data);
    applyToPreview(data);
    renderHistory();
});
