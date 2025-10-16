let products = [
    { id: 1, name: "Produto A", category: "Bebidas", price: 10.5, unit: "Unidade", qty: 50 },
    { id: 2, name: "Produto B", category: "Alimentos", price: 5.0, unit: "Kg", qty: 30 }
];

function loadProducts(list = products) {
    const tbody = document.querySelector("#productsTable tbody");
    tbody.innerHTML = "";
    list.forEach(p => {
        const tr = document.createElement("tr");
        tr.innerHTML = `
      <td>${p.id}</td>
      <td>${p.name}</td>
      <td>${p.category}</td>
      <td>${p.price.toFixed(2)}</td>
      <td>${p.unit}</td>
      <td>${p.qty}</td>
    `;
        tr.addEventListener("click", () => {
            document.querySelectorAll("#productsTable tr").forEach(row => row.classList.remove("selected"));
            tr.classList.add("selected");
        });
        tbody.appendChild(tr);
    });
}

document.getElementById("btnSearch").addEventListener("click", () => {
    const term = prompt("Digite o nome do produto para buscar:");
    if (term) {
        const filtered = products.filter(p => p.name.toLowerCase().includes(term.toLowerCase()));
        loadProducts(filtered);
    }
});

document.getElementById("btnLoadAll").addEventListener("click", () => {
    loadProducts();
});

document.getElementById("btnEdit").addEventListener("click", () => {
    const selected = document.querySelector("#productsTable tr.selected");
    if (!selected) {
        alert("Selecione um produto para editar.");
        return;
    }
    const id = parseInt(selected.cells[0].textContent);
    const product = products.find(p => p.id === id);

    const newPrice = prompt("Novo preço unitário:", product.price);
    const newQty = prompt("Nova quantidade:", product.qty);

    if (newPrice !== null && newQty !== null) {
        product.price = parseFloat(newPrice);
        product.qty = parseInt(newQty);
        loadProducts();
        alert("Produto atualizado com sucesso!");
    }
});

document.getElementById("btnDelete").addEventListener("click", () => {
    const selected = document.querySelector("#productsTable tr.selected");
    if (!selected) {
        alert("Selecione um produto para excluir.");
        return;
    }
    const id = parseInt(selected.cells[0].textContent);
    if (confirm("Tem certeza que deseja excluir o produto?")) {
        products = products.filter(p => p.id !== id);
        loadProducts();
        alert("Produto excluído com sucesso!");
    }
});

document.getElementById("menuLeave").addEventListener("click", () => {
    if (confirm("Deseja sair?")) {
        window.location.href = "../login/index.html";
    }
});

const currentUser = { userType: "ADMIN" };
if (currentUser.userType === "EMPLOYEE") {
    document.getElementById("menuRegisterProduct").style.pointerEvents = "none";
    document.getElementById("menuRegisterProduct").style.opacity = "0.5";
    document.getElementById("menuSalesReport").style.pointerEvents = "none";
    document.getElementById("menuSalesReport").style.opacity = "0.5";
}

loadProducts();
