function getData(url, type, data) {
	return $.ajax({
		headers: {
			'Content-Type': 'application/json'
		},
		url: url,
		type: type,
		data: data
	});
}

function deleteRows() {
	document.querySelectorAll('tbody>tr').forEach(element => {
		element.remove();
	});
}

function createTable(array) {
	deleteRows();
	if (array.length === 0) {
		const tr = document.createElement('tr');
		const td = document.createElement('td');
		td.setAttribute('colspan', 4);
		td.innerText = 'No Books in Database';
		tr.append(td);
		document.querySelector('tbody').append(tr);
	} else {
		array.forEach(element => {
			const tr = document.createElement('tr');
			const title = document.createElement('td');
			title.innerText = element.title;
			tr.append(title);
			const author = document.createElement('td');
			author.innerText = element.author.name + ' ' + element.author.surname;
			tr.append(author);
			const genres = document.createElement('td');
			element.genres.forEach(item => {
				genres.innerHTML += item.name + '<br />';
			});
			tr.append(genres);
			const actions = document.createElement('td');
			actions.setAttribute('class', 'd-flex justify-content-center align-items-center flex-wrap gap-2');
			const edit = document.createElement('button');
			edit.setAttribute('type', 'button');
			edit.setAttribute('class', 'btn btn-primary');
			edit.innerText = 'Edit';
			actions.append(edit);
			const deleteBtn = document.createElement('button');
			deleteBtn.setAttribute('type', 'button');
			deleteBtn.setAttribute('class', 'btn btn-danger');
			deleteBtn.innerText = 'Delete';
			actions.append(deleteBtn);
			tr.append(actions);
			document.querySelector('tbody').append(tr);
			edit.onclick = function() {
				initBookForm('update', element);
			};
			deleteBtn.onclick = function() {
				deleteBook(element.id);
			};
		});
	}
}

async function initTable() {
	deleteRows();
	const tr = document.createElement('tr');
	const td = document.createElement('td');
	td.setAttribute('colspan', 4);
	td.innerText = 'Loading...';
	tr.append(td);
	document.querySelector('tbody').append(tr);
	try {
		const res = await getData('http://localhost:8080/api/books', 'GET');
		createTable(res);
	} catch (err) {
		console.log(err);
	}
}

async function initBookForm(type, book) {
	document.querySelector('aside').style.display = 'flex';
	document.getElementById('loading').style.display = 'block';
	document.querySelectorAll('#hidden').forEach(element => {
		element.remove();
	});
	try {
		const authors = await getData('http://localhost:8080/api/authors', 'GET');
		const genres = await getData('http://localhost:8080/api/genres', 'GET');
		if (authors.length === 0 || genres.length === 0) {
			document.getElementById('loading').style.display = 'none';
			document.getElementById('no-book-form').style.display = 'block';
		} else {
			document.querySelectorAll('#book-form option').forEach(element => {
				element.remove();
			});
			authors.forEach(element => {
				const option = document.createElement('option');
				option.value = element.id;
				if (type === 'update' && option.value == book.author.id && book) {
					option.setAttribute('selected', 'selected');
				}
				option.innerText = element.name + ' ' + element.surname;
				document.querySelector('#book-form select').append(option);
			});
			document.querySelectorAll('#checkbox-container>div').forEach(element => {
				element.remove();
			});
			genres.forEach(element => {
				const checkbox = document.createElement('input');
				checkbox.setAttribute('type', 'checkbox');
				checkbox.required = true;
				checkbox.id = element.id;
				checkbox.value = element.id;
				if (type === 'update' && book && book.genres.find(item => item.id == checkbox.value)) {
					checkbox.setAttribute('checked', 'checked');
				}
				checkbox.setAttribute('name', 'genres');
				checkbox.setAttribute('class', 'checkboxes');
				const label = document.createElement('label');
				label.setAttribute('for', element.id);
				label.innerText = element.name;
				const div = document.createElement('div');
				div.setAttribute('class', 'd-flex align-items-center gap-2');
				div.append(checkbox);
				div.append(label);
				document.getElementById('checkbox-container').append(div);
			});
			let checkboxes = $('.checkboxes');
			checkboxes.change(function() {
				if ($('.checkboxes:checked').length > 0) {
					checkboxes.removeAttr('required');
				} else {
					checkboxes.attr('required', 'required');
				}
			});
			document.getElementById('loading').style.display = 'none';
			document.querySelectorAll('#book-form button').forEach(element => {
				element.style.display = 'none';
			});
			switch (type) {
				case 'add':
					document.getElementById('add-book-btn').style.display = 'block';
					document.getElementById('form-title').innerText = 'Add New Book:';
					break;
				case 'update':
					document.getElementById('update-book-btn').style.display = 'block';
					document.getElementById('form-title').innerText = 'Update Book:';
					document.getElementById('title').value = book.title;
					const hidden = document.createElement('input');
					hidden.setAttribute('type', 'hidden');
					hidden.id = 'hidden';
					hidden.value = book.id;
					document.getElementById('book-form').prepend(hidden);
					break;
			}
			document.getElementById('book-form').style.display = 'flex';
		}
	} catch (err) {
		console.log(err);
	}
}

async function addAuthor() {
	const author = {
		name: document.getElementById('name-author').value,
		surname: document.getElementById('surname-author').value
	};
	try {
		const response = await getData('http://localhost:8080/api/authors', 'POST', JSON.stringify(author));
		alert(response);
		closeModal();
	} catch (err) {
		alert('Error: Something went wrong. Try Again!');
		console.log(err);
	}
}

async function addGenre() {
	const genre = {
		name: document.getElementById('name-genre').value
	};
	try {
		const response = await getData('http://localhost:8080/api/genres', 'POST', JSON.stringify(genre));
		alert(response);
		closeModal();
	} catch (err) {
		alert('Error: Something went wrong. Try Again!');
		console.log(err);
	}
}

async function addBook() {
	const book = {
		title: document.getElementById('title').value,
		author: {
			id: document.querySelector('#book-form select').value
		},
		genres: []
	};
	document.querySelectorAll('#book-form .checkboxes').forEach(element => {
		if (element.checked) {
			book.genres.push({ id: element.value });
		}
	});
	try {
		const response = await getData('http://localhost:8080/api/books', 'POST', JSON.stringify(book));
		alert(response);
		closeModal();
		initTable();
	} catch (err) {
		alert('Error: Something went wrong. Try Again!');
		console.log(err);
	}
}

async function deleteBook(id) {
	try {
		await getData('http://localhost:8080/api/books/' + id, 'DELETE');
		closeModal();
		initTable();
	} catch (err) {
		alert('Error: Something went wrong. Try Again!');
		console.log(err);
	}
}

async function updateBook(id) {
	const book = {
		id: id,
		title: document.getElementById('title').value,
		author: {
			id: document.querySelector('#book-form select').value
		},
		genres: []
	};
	document.querySelectorAll('#book-form .checkboxes').forEach(element => {
		if (element.checked) {
			book.genres.push({ id: element.value });
		}
	});
	try {
		const response = await getData('http://localhost:8080/api/books/' + id, 'PUT', JSON.stringify(book));
		alert(response);
		closeModal();
		initTable();
	} catch (err) {
		alert('Error: Something went wrong. Try Again!');
		console.log(err);
	}
}

function openModal(type) {
	document.querySelector('aside').style.display = 'flex';
	document.getElementById(type).style.display = 'flex';
}

function closeModal() {
	document.getElementById('loading').style.display = 'none';
	document.getElementById('no-book-form').style.display = 'none';
	document.querySelectorAll('form').forEach(element => {
		element.style.display = 'none';
	});
	document.querySelector('aside').style.display = 'none';
	document.querySelectorAll('input').forEach(element => {
		element.value = '';
	});
}

window.addEventListener('DOMContentLoaded', () => {
	initTable();
	document.getElementById('add-author').addEventListener('click', () => {
		openModal('author-form');
	});
	document.getElementById('add-genre').addEventListener('click', () => {
		openModal('genre-form');
	});
	document.getElementById('add-book').addEventListener('click', () => {
		initBookForm('add');
	});
	document.querySelector('aside').addEventListener('click', (event) => {
		if (event.target === document.querySelector('aside')) {
			closeModal();
		}
	});
	document.getElementById('author-form').addEventListener('submit', (event) => {
		event.preventDefault();
		addAuthor();
	});
	document.getElementById('genre-form').addEventListener('submit', (event) => {
		event.preventDefault();
		addGenre();
	});
	document.getElementById('book-form').addEventListener('submit', (event) => {
		event.preventDefault();
		switch (event.submitter.id) {
			case 'add-book-btn':
				addBook();
				break;
			case 'update-book-btn':
				updateBook(document.getElementById('hidden').value);
				break;
		}
	});
});