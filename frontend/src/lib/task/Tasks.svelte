<script>
    import {deleteAll, deleteTask, findAll, findAllTaskStatuses, updateTask} from "./service/task-service.js";
    import {onMount} from "svelte";
    import trash_can from '../../assets/trash_can.svg';
    import {addToast} from "../../store.js";
    import SimpleButton from "../common/SimpleButton.svelte";
    import Task from "./Task.svelte";
    import SearchTask from "./SearchTask.svelte";
    import DeletionConfirmation from "./DeletionConfirmation.svelte";

    let unfilteredTasks = [];
    let tasks = [];
    let taskStatuses = [];
    $: numTasks = tasks.length;
    let showTaskModal = false;
    let showDeletionConfirmation = false;
    let chosenTask;
    const taskStatusToBadgeStyle = {
        SCHEDULED: "inline-flex items-center rounded-md bg-yellow-100 px-2 py-1 text-xs font-medium text-yellow-800 ring-1 ring-inset ring-yellow-700/10",
        CANCELED: "inline-flex items-center rounded-md bg-gray-100 px-2 py-1 text-xs font-medium text-gray-700 ring-1 ring-inset ring-gray-700",
        STARTED: "inline-flex items-center rounded-md bg-blue-100 px-2 py-1 text-xs font-medium text-blue-700 ring-1 ring-inset ring-blue-700/10",
        DONE: "inline-flex items-center rounded-md bg-green-50 px-2 py-1 text-xs font-medium text-green-700 ring-1 ring-inset ring-green-800/20"
    }

    const handleFindAll = async () => {
        try {
            const response = await findAll();
            if (response.ok) {
                unfilteredTasks = await response.json();
                tasks = unfilteredTasks;
                if (unfilteredTasks.length > 0) {
                    addToast({message: "Successfully fetched all tasks.", type: "success"});
                } else {
                    addToast({message: "No tasks were found.", type: "info"});
                }
            } else {
                addToast({message: "Failed to fetch all tasks.", type: "error"});
            }
        } catch (error) {
            console.log(error);
            addToast({message: "Failed to fetch all tasks.", type: "error"});
        }
    }

    const handleFindAllTaskStatuses = async () => {
        try {
            const response = await findAllTaskStatuses();
            if (response.ok) {
                taskStatuses = await response.json();
                console.log("Successfully fetched all task statuses.");
            } else {
                console.log("Failed to fetch all task statuses.");
            }
        } catch (error) {
            console.log(error);
            console.log("Failed to fetch all task statuses.");
        }
    }

    const searchTask = (event) => {
        const searchAttribute = event.detail.searchAttribute;
        const searchInput = event.detail.searchInput.toLowerCase();
        tasks = unfilteredTasks.filter(task => task[searchAttribute].toLowerCase().includes(searchInput));
    }

    const resetFilter = () => {
        tasks = unfilteredTasks;
    }

    const handleDeleteAll = async () => {
        try {
            const response = await deleteAll();
            if (response.ok) {
                await handleFindAll();
                addToast({message: "Successfully deleted all tasks.", type: "success"});
            } else {
                addToast({message: "Failed to delete all tasks.", type: "error"});
            }
        } catch (error) {
            console.log(error);
            addToast({message: "Failed to delete all tasks.", type: "error"});
        }
    }

    const handleDeleteTask = async (task) => {
        try {
            const response = await deleteTask(task);
            if (response.ok) {
                await handleFindAll();
                addToast({message: "Successfully deleted task.", type: "success"});
            } else {
                addToast({message: "Failed to delete task.", type: "error"});
            }
        } catch (error) {
            console.log(error);
            addToast({message: "Failed to delete task.", type: "error"});
        }
    }

    const handleShowTaskModal = (task) => {
        chosenTask = task;
        showTaskModal = true;
    }

    const updateChosenTaskData = async (event) => {
        try {
            const response = await updateTask(chosenTask, event.detail);
            if (response.ok) {
                chosenTask = await response.json();
                await handleFindAll();
                addToast({message: "Successfully updated task.", type: "success"});
            } else {
                addToast({message: "Failed to update task.", type: "error"});
            }
        } catch (error) {
            console.log(error);
            addToast({message: "Failed to update task.", type: "error"});
        }
    }

    onMount(handleFindAll);
    onMount(handleFindAllTaskStatuses);
</script>

<div class="container flex flex-col items-center px-6 mx-auto mt-10 text-white">
    <img src="/logo.png" alt="logo" class="w-96 h-36"/>
    <div class="flex flex-row mt-10 md:mt-20 place-self-start">
        <SimpleButton text="Reload Tasks" on:click={handleFindAll}></SimpleButton>
        {#if numTasks === unfilteredTasks.length && numTasks > 0}
            <button on:click={() => showDeletionConfirmation = true} class="bg-darkCharcoal text-red-600 p-2 ml-2 border-2 border-white rounded md:hover:shadow-md md:hover:shadow-white">
                Delete All <img src={trash_can} class="inline mb-1 w-4 h-4" alt="Delete Task">
            </button>
        {/if}
    </div>
    <div class="w-full animate-fadeInRightToLeft place-self-start mt-4">
        {#key unfilteredTasks}
            <SearchTask on:searchTask={searchTask} on:resetFilter={resetFilter}></SearchTask>
        {/key}
    </div>
    {#if numTasks > 0}
        <div class="animate-fadeInRightToLeft w-full overflow-x-scroll mt-4">
            <table class="md:table-auto w-full text-sm text-left rtl:text-right text-gray-400">
                <thead class="uppercase bg-gray-300 text-gray-700">
                    <tr>
                        <th class="py-2 pl-6">
                            Description
                        </th>
                        <th class="py-2 pl-6">
                            Execution date
                        </th>
                        <th class="py-2 pl-6">
                            Status
                        </th>
                        <th class="py-2 pl-6">
                        </th>
                    </tr>
                </thead>
                <tbody>
                    {#each tasks as task, index (index)}
                        <tr class="border-b bg-whitesmoke border-gray-700 text-gray-700 hover:scale-y-110 hover:scale-x-[1.01] hover:bg-gray-200">
                            <td on:click={() => handleShowTaskModal(task)} class="px-6 py-4 cursor-pointer">
                                {task.description || 'n/a'}
                            </td>
                            <td on:click={() => handleShowTaskModal(task)} class="px-6 py-4 cursor-pointer">
                                {task.executionDate || 'n/a'}
                            </td>
                            <td on:click={() => handleShowTaskModal(task)} class="px-6 py-4 cursor-pointer">
                                <span class={taskStatusToBadgeStyle[task.taskStatus]}>{task.taskStatus.toLowerCase()}</span>
                            </td>
                            <td class="px-6 py-4">
                                <button on:click={handleDeleteTask(task)}>
                                    <img src={trash_can} class="mr-3 md:mr-0 w-4 h-4" alt="Delete Task">
                                </button>
                            </td>
                        </tr>
                    {/each}
                </tbody>
            </table>
        </div>
        <Task
            bind:showModal={showTaskModal}
            task={chosenTask}
            on:updateTaskData={updateChosenTaskData}
            {taskStatuses}>
        </Task>
        <DeletionConfirmation bind:showModal={showDeletionConfirmation} on:deleteAllTasks={handleDeleteAll}></DeletionConfirmation>
    {/if}
</div>