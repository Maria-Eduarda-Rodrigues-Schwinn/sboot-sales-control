const categories = ["Bebidas", "Alimentos", "Limpeza"];
const units = ["Unidade", "Litro", "Quilo"];

const categorySelect = document.getElementById("category");
categories.forEach(cat => {
    const opt = document.createElement("option");
    opt.value = cat;
    opt.textContent = cat;
    categorySelect.appendChild(opt);
});

const unitSelect = document.getElementById("unitOfMeasure");
units.forEach(unit => {
    const opt = document.createElement("option");
    opt.value = unit;
    opt.textContent = unit;
    unitSelect.appendChild(opt);
});

document.getElementById("btnClear").addEventListener("click", () => {
    document.getElementById("productForm").reset();
});

document.getElementById("productForm").addEventListener("submit", (e) => {
    e.preventDefault();

    const name = document.getElementById("name").value.trim();
    const quantity = document.getElementById("quantity").value.trim();
    const unitPrice = document.getElementById("unitPrice").value.trim();
    const category = categorySelect.value;
    const unit = unitSelect.value;

    if (!name || !quantity || !unitPrice || !category || !unit) {
        alert("Preencha todos os campos!");
        return;
    }

    if (isNaN(quantity) || quantity <= 0) {
        alert("Quantidade inválida!");
        return;
    }

    if (isNaN(unitPrice) || unitPrice <= 0) {
        alert("Preço inválido!");
        return;
    }

    alert("Produto adicionado com sucesso!");
    document.getElementById("productForm").reset();
});

document.getElementById("menuLeave").addEventListener("click", () => {
    if (confirm("Deseja sair?")) {
        window.location.href = "../login/index.html";
    }
});

const currentUser = { userType: "ADMIN" };
if (currentUser.userType === "EMPLOYEE") {
    document.getElementById("menuEditProduct").style.pointerEvents = "none";
    document.getElementById("menuEditProduct").style.opacity = "0.5";
    document.getElementById("menuSalesReport").style.pointerEvents = "none";
    document.getElementById("menuSalesReport").style.opacity = "0.5";
}
