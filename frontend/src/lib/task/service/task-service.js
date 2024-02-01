const baseUrl = import.meta.env.VITE_BASE_URL;

const formatDate = (date) => {
    let dateParts = date.split('.');
    const dateFormatter = new Intl.DateTimeFormat('fr-CA', {
        day: '2-digit',
        month: '2-digit',
        year: 'numeric',
    });
    return dateFormatter.format(new Date(dateParts[2], dateParts[1] - 1, dateParts[0]));
} // Change pattern from dd.MM.yyyy to yyyy-MM-dd


export const findAll = async () => {
    const serviceUrl = `${baseUrl}/tasks`;
    try {
        return await fetch(serviceUrl, {
            method: 'GET',
            mode: 'cors',
            keepalive: true,
            headers: {
                "Accept": "application/json",
            }
        });
    } catch (error) {
        console.log(error);
        throw error;
    }
}

export const deleteAll = async () => {
    const serviceUrl = `${baseUrl}/tasks/all`;
    try {
        return await fetch(serviceUrl, {
            method: 'DELETE',
            mode: 'cors',
            keepalive: true,
            headers: {
                "Accept": "application/json",
            }
        });
    } catch (error) {
        console.log(error);
        throw error;
    }
}

export const deleteTask = async (task) => {
    const serviceUrl = `${baseUrl}/tasks?description=${task.description}&executionDate=${formatDate(task.executionDate)}`;
    try {
        return await fetch(serviceUrl, {
            method: 'DELETE',
            mode: 'cors',
            keepalive: true,
            headers: {
                "Accept": "application/json",
            }
        });
    } catch (error) {
        console.log(error);
        throw error;
    }
}

export const updateTask = async (task, updateTaskRequestBody) => {
    if (updateTaskRequestBody.newDescription !== undefined ||
        updateTaskRequestBody.newExecutionDate !== undefined ||
        updateTaskRequestBody.newTaskStatus !== undefined) {
        const fixedDate = formatDate(task.executionDate)
        const serviceUrl = `${baseUrl}/tasks`;
        try {
            return await fetch(serviceUrl, {
                method: 'PUT',
                mode: 'cors',
                keepalive: true,
                headers: {
                    "Accept": "application/json",
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    taskDTO: {
                        description: task.description,
                        executionDate: fixedDate,
                        taskStatus: task.taskStatus
                    },
                    newDescription: updateTaskRequestBody.newDescription || task.description,
                    newExecutionDate: updateTaskRequestBody.newExecutionDate || fixedDate,
                    newTaskStatus: updateTaskRequestBody.newTaskStatus || task.taskStatus
                })
            });
        } catch (error) {
            console.log(error);
            throw error;
        }
    }
}

export const findAllTaskStatuses = async () => {
    const serviceUrl = `${baseUrl}/tasks/statuses`;
    try {
        return await fetch(serviceUrl, {
            method: 'GET',
            mode: 'cors',
            keepalive: true,
            headers: {
                "Accept": "application/json",
            }
        });
    } catch (error) {
        console.log(error);
        throw error;
    }
}