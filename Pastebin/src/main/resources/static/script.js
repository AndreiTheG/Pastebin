function input() {
    const valueInput = document.querySelector('textarea').value;
    if (valueInput.length == 0) {
        alert('You must enter some characters!');
    }
}