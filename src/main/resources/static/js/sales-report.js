let sales = [
    {
        id: 1,
        date: "2025-09-01",
        products: [
            { name: "Produto A", price: 10.5, unit: "Unidade", qty: 2, category: "Bebidas" }
        ],
        total: 21.0
    },
    {
        id: 2,
        date: "2025-09-05",
        products: [
            { name: "Produto B", price: 5.0, unit: "Kg", qty: 3, category: "Alimentos" }
        ],
        total: 15.0
    }
];

function loadSales(list = sales) {
    const tbody = document.querySelector("#salesTable tbody");
    tbody.innerHTML = "";
    list.forEach(sale => {
        sale.products.forEach(p => {
            const tr = document.createElement("tr");
            tr.innerHTML = `
        <td>${sale.id}</td>
        <td>${sale.date}</td>
        <td>${p.name}</td>
        <td>${p.price.toFixed(2)}</td>
        <td>${p.unit}</td>
        <td>${p.qty}</td>
        <td>${sale.total.toFixed(2)}</td>
      `;
            tbody.appendChild(tr);
        });
    });
}

document.getElementById("btnFilter").addEventListener("click", () => {
    const name = document.getElementById("productName").value.toLowerCase();
    const category = document.getElementById("categoryFilter").value;
    const from = document.getElementById("fromDate").value;
    const to = document.getElementById("toDate").value;

    const filtered = sales.filter(sale => {
        const saleDate = sale.date;
        const inDateRange = (!from || saleDate >= from) && (!to || saleDate <= to);
        return inDateRange && sale.products.some(p => {
            const matchName = !name || p.name.toLowerCase().includes(name);
            const matchCategory = category === "Todas" || p.category === category;
            return matchName && matchCategory;
        });
    });

    if (filtered.length === 0) {
        alert("Nenhuma venda encontrada para os critérios especificados.");
    }
    loadSales(filtered);
});

document.getElementById("btnShowAll").addEventListener("click", () => {
    loadSales();
});

document.getElementById("btnExport").addEventListener("click", () => {
    const rows = [["ID Venda", "Data da Venda", "Nome do Produto", "Preço Unitário (R$)", "Unidade", "Quantidade", "Valor Total"]];
    document.querySelectorAll("#salesTable tbody tr").forEach(tr => {
        const cols = Array.from(tr.querySelectorAll("td")).map(td => td.textContent);
        rows.push(cols);
    });

    if (rows.length === 1) {
        alert("Não há dados para exportar.");
        return;
    }

    const csvContent = rows.map(e => e.join(",")).join("\n");
    const blob = new Blob([csvContent], { type: "text/csv;charset=utf-8;" });
    const link = document.createElement("a");
    link.href = URL.createObjectURL(blob);
    link.download = `vendas_${new Date().toISOString().replace(/[:.]/g, "-")}.csv`;
    link.click();
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
    document.getElementById("menuEditProduct").style.pointerEvents = "none";
    document.getElementById("menuEditProduct").style.opacity = "0.5";
}

loadSales();
