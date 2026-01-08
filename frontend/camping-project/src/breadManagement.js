let broodjes = JSON.parse(localStorage.getItem('broodjes') || '[]');
let bewerkIndex = null;

const lijst = document.getElementById('broodjesLijst');
const leegText = document.getElementById('leegText');
const form = document.getElementById('broodjeForm');
const naam = document.getElementById('naam');
const prijs = document.getElementById('prijs');
const beschrijving = document.getElementById('beschrijving');
const foto = document.getElementById('foto');
const preview = document.getElementById('preview');
const zoek = document.getElementById('zoek');
const formTitel = document.getElementById('formTitel');
const submitBtn = document.getElementById('submitBtn');

function toonBroodjes() {
    lijst.innerHTML = '';
    let gefilterd = broodjes.filter(b => b.naam.toLowerCase().includes(zoek.value.toLowerCase()));
    leegText.style.display = gefilterd.length ? 'none' : 'block';
    gefilterd.forEach((b, i) => {
        let div = document.createElement('div');
        div.className = 'broodje';
        div.innerHTML = `
      <img src="${b.foto || ''}" alt="foto">
      <div class='info'><b>${b.naam}</b><br>€${b.prijs}<br>${b.beschrijving}</div>
      <div>
        <button onclick='bewerk(${i})' class='btn-tonal'><span class="material-icons">edit</span></button>
        <button onclick='verwijder(${i})' class='btn-danger'><span class="material-icons">delete</span></button>
      </div>`;
        lijst.appendChild(div);
    });
}

function verwijder(i) {
    broodjes.splice(i, 1);
    localStorage.setItem('broodjes', JSON.stringify(broodjes));
    toonBroodjes();
    resetForm();
}

function bewerk(i) {
    const b = broodjes[i];
    bewerkIndex = i;
    naam.value = b.naam;
    prijs.value = b.prijs;
    beschrijving.value = b.beschrijving;
    preview.src = b.foto || '';
    formTitel.textContent = 'Broodje bewerken';
    submitBtn.textContent = 'Opslaan';
    submitBtn.className = 'bewerken';
}

form.onsubmit = e => {
    e.preventDefault();
    let reader = new FileReader();
    reader.onload = function() {
        let data = {
            naam: naam.value,
            prijs: prijs.value,
            beschrijving: beschrijving.value,
            foto: reader.result || preview.src
        };
        if (bewerkIndex !== null) {
            broodjes[bewerkIndex] = data;
            bewerkIndex = null;
            formTitel.textContent = 'Nieuw broodje';
            submitBtn.textContent = 'Toevoegen';
            submitBtn.className = 'toevoegen';
        } else {
            broodjes.push(data);
        }
        localStorage.setItem('broodjes', JSON.stringify(broodjes));
        form.reset();
        preview.src = '';
        toonBroodjes();
    }
    if (foto.files[0]) reader.readAsDataURL(foto.files[0]); else reader.onload();
}

foto.onchange = () => {
    const file = foto.files[0];
    if (!file) { preview.src = ''; return; }
    const r = new FileReader();
    r.onload = e => preview.src = e.target.result;
    r.readAsDataURL(file);
}

function resetForm() {
    form.reset();
    preview.src = '';
    formTitel.textContent = 'Nieuw broodje';
    submitBtn.textContent = 'Toevoegen';
    submitBtn.className = 'toevoegen';
    bewerkIndex = null;
}

zoek.oninput = toonBroodjes;
toonBroodjes();

